package bundleloadercheck;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrgencyBundleVersionHolder {

    private static final UrgencyBundleVersionHolder INSTANCE = new UrgencyBundleVersionHolder();

    /*
        Default scheduled executor thread factory uses non-daemon threads which blocks
        JVM shutdown. Using this custom thread factory fixes that with the caveat that
        the scheduled task *may* not run before the JVM exits which shouldn't be a
        problem for this use case.
    */
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1, new DaemonThreadFactory());

    private static final ConcurrentMap<String, String> bundleVersions = new ConcurrentHashMap<>();

    private static final ConcurrentMap<String, Map<String, List<String>>> bundleAttributes = new ConcurrentHashMap<>();

    private static final String LOCAL_FILE_NAME = "versions/bundle-versions.json";

    private static final String CLASSPATH_FILE_NAME = "versions/urgency-bundle-versions.json";

    private static final String VERSION_FILE_NAME = "bundle-versions.json";

    private static final String APPLICATION_NAME = "urgency-ui-web";

    private static final int VERSION_POLLING_DELAY_IN_SECONDS = 10; // 15 minutes

    private static final Logger LOGGER = LoggerFactory.getLogger(UrgencyBundleVersionHolder.class);
    
    private static final URL VERSIONED_S3_LINK;
    
    private static final String FULL_URL = "http://s3.amazonaws.com/ewe-assets/bundles/"+ APPLICATION_NAME+ "/versions/"+ VERSION_FILE_NAME;
    
    private static final ConcurrentMap<String, Long> URL_LASTUPDATED_MAP = new ConcurrentHashMap<>();

    static{
    	URL url = null;
    	try {
    		url = new URL(FULL_URL);
		} catch (MalformedURLException e) {
			LOGGER.error("Could not make S3 URL " + FULL_URL);
			throw new ExceptionInInitializerError(e);
		}
    	finally{
    		VERSIONED_S3_LINK = url;
    	}
    }


    private volatile boolean bundleConfiguration;

    private UrgencyBundleVersionHolder() {}

    public static UrgencyBundleVersionHolder getInstance(boolean config) {
        INSTANCE.bundleConfiguration = config;
        return INSTANCE;
    }

    /*
     * Load Bundle versions at startup and schedule job for periodic updates
     */
    public void loadVersionsAndSchedule() {
        loadBundleVersions();
        scheduleUpdateIfEnabled();
    }

    private void scheduleUpdateIfEnabled() {
        scheduledExecutorService.scheduleWithFixedDelay(new VersionsUpdater(), VERSION_POLLING_DELAY_IN_SECONDS, VERSION_POLLING_DELAY_IN_SECONDS, TimeUnit.SECONDS);
    }

    public void loadBundleVersions() {
        if (bundleConfiguration) {
            updateBundleVersionsFromS3WithFallback();
        } else {
            updateBundleVersionsFromClasspath();
        }
    }

    private void updateBundleVersionsFromS3WithFallback() {
        BundlePropertiesCollection bpc = null;
        try {
        	
        	LOGGER.info("Checking Timestamp before download of the Urgency Bundle versions file from S3 {}", FULL_URL);
        	long timestamp = IOUtil.lastModified(VERSIONED_S3_LINK);
        	if(!URL_LASTUPDATED_MAP.containsKey(FULL_URL)|| URL_LASTUPDATED_MAP.get(FULL_URL)!=timestamp)
        	{
        		LOGGER.info("Attempting download of the Urgency Bundle versions file from S3 {}", FULL_URL);
                bpc = IOUtil.read(VERSIONED_S3_LINK, BundlePropertiesCollection.class);
                LOGGER.info("Successfully downloaded the Urgency Bundle versions file from S3 {}", FULL_URL);
                updateBundleVersions(bpc);
                makeLocalCopy(bpc);
                // after we make local copy we can update URL_LASTUPDATED_MAP
                URL_LASTUPDATED_MAP.put(FULL_URL, timestamp);
        	}
        	else
        	{
        		LOGGER.info("Did not download Urgency Bundle from S3 as last downloaded timestamp {} Urgency Bundle versions already in memory",  new DateTime(timestamp));
        	}
        } catch (IOException e) {
        	LOGGER.error("Could not load Urgency Bundle versions file from S3 {}. Trying to load it locally",FULL_URL,e);
            if (bpc == null) {
                if (localFileExists()) {
                    updateBundleVersionsFromLocalFile();
                } else {
                    updateBundleVersionsFromClasspath();
                }
            }
        }
    }

    private boolean localFileExists() {
        return Files.exists(Paths.get(LOCAL_FILE_NAME));
    }

    private void updateBundleVersionsFromClasspath() {
        BundlePropertiesCollection bpc = null;
        try {
            LOGGER.info("Attempting read of the Urgency Bundle versions file from CLASSPATH_FILE_NAME {}", CLASSPATH_FILE_NAME);
            bpc = IOUtil.read(getClass().getClassLoader().getResource(CLASSPATH_FILE_NAME), BundlePropertiesCollection.class);
            LOGGER.info("Successfully read the Urgency Bundle versions file from CLASSPATH_FILE_NAME {}", CLASSPATH_FILE_NAME);
            updateBundleVersions(bpc);
        } catch (IOException e) {
            LOGGER.error("Could not load backup version JSON file from classpath", e);
        }
    }

    private void makeLocalCopy(final BundlePropertiesCollection bpc) {
        try {
        	LOGGER.info("Attempting making local copy at {}", CLASSPATH_FILE_NAME);
            if (bpc != null) {
                final Path path = Paths.get(LOCAL_FILE_NAME);
                final Path parent = path.getParent();

                if (parent != null && !Files.exists(parent)) {
                    Files.createDirectories(parent);
                }

                IOUtil.write(path.toFile(), bpc);
                LOGGER.info("Success making local copy at {}", CLASSPATH_FILE_NAME);
            }
        } catch (Exception e) {
            LOGGER.error("Exception while making local copy of bundle versions file", e);
        }
    }

    private void updateBundleVersionsFromLocalFile() {
        try {
        	LOGGER.info("Trying to read locally from {}",Paths.get(LOCAL_FILE_NAME).toFile().getName());
            updateBundleVersions(IOUtil.read(Paths.get(LOCAL_FILE_NAME).toFile(), BundlePropertiesCollection.class));
        } catch (Exception e) {
            LOGGER.error("Could not read local copy of previously downloaded version JSON", e);
        }
    }

    private void updateBundleVersions(final BundlePropertiesCollection versions) throws IOException{
        for (final Map.Entry<String, BundleProperties> entry : versions.getBundles().entrySet()) {
            final String bundle = entry.getKey();
            final String newVersion = entry.getValue().getChecksum();
            final String currentVersion = getVersionFor(bundle);
            if (currentVersion == null || !currentVersion.equals(newVersion)) {
                putVersion(bundle, entry.getValue().getChecksum());
                putAttributes(bundle, entry.getValue().getAttributes());
            }
        }
        LOGGER.info("Successfully updated the BundlePropertiesCollection");
    }

    private String getVersionFor(String bundle) {
        return bundleVersions.get(bundle);
    }

    private Map<String, List<String>> getAttributesFor(String bundle) { return bundleAttributes.get(bundle); }

    private void putAttributes(String bundle, Map<String, List<String>> attributes) {
        bundleAttributes.put(bundle, attributes);
    }

    private void putVersion(String bundle, String version) {
        bundleVersions.put(bundle, version);
    }

    public String getVersionFor(String bundleGroup, String bundleName) {
        return getVersionFor(bundleGroup + "-" + bundleName);
    }

    public boolean containsVersionFor(String bundleGroup, String bundleName) {
        return bundleVersions.containsKey(bundleGroup + "-" + bundleName);
    }

    public Map<String, List<String>> getAttributesFor(String bundleGroup, String bundleName) {
        return getAttributesFor(bundleGroup + "-" + bundleName);
    }

    public Map<String, String> getAllBundleVersions() {
        return Collections.unmodifiableMap(bundleVersions);
    }

    @SuppressWarnings({"PMD.DoNotUseThreads", "PMD.AvoidCatchingThrowable"})
    private class VersionsUpdater implements Runnable {
        @Override
        public void run() {
            try {
                loadBundleVersions();
            } catch (Throwable t) {
                LOGGER.error("Failed to run the scheduled urgency bundles version update job", t);
            }
        }
    }

    @SuppressWarnings("PMD.DoNotUseThreads")
    private class DaemonThreadFactory implements ThreadFactory {
        // Volatile since this could be called by multiple threads
        private volatile int n = 1;
        @Override
        public Thread newThread(final Runnable r) {
            final Thread t = new Thread(r);
            t.setDaemon(true);
            t.setName(String.format("urgency-bundling-version-updater-thread-%d", n));
            n++;
            return t;
        }
    }
}
