package bundleloadercheck;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

public class IOUtil {
        private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
                .enable(SerializationFeature.CLOSE_CLOSEABLE)
                .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        /**
         * Timeout in milliseconds
         */
        private static final int DEFAULT_CONNECTION_TIMEOUT = 5000;

        /**
         * Timeout in milliseconds
         */
        private static final int DEFAULT_READ_TIMEOUT = 30000;

        private IOUtil() {
            // Utility class
        }

        public static long lastModified(final URL url) throws IOException {
            final URLConnection connection = openConnection(url);
            try (final InputStream is = connection.getInputStream()) {
                return connection.getLastModified();
            }
        }

        public static <T> T read(final URL url, Class<T> type) throws IOException {
            final URLConnection connection = openConnection(url);
            try (final InputStream is = connection.getInputStream()) {
            	InputStreamReader isr = null;
                if ("gzip".equals(connection.getContentEncoding())) {
                	isr = new InputStreamReader(new GZIPInputStream(is));
                }
                else {
                	isr = new InputStreamReader(is);
                }
            	return OBJECT_MAPPER.readValue(isr, type);
            }
        }

        public static <T> T read(final File file, Class<T> type) throws IOException {
            return OBJECT_MAPPER.readValue(file, type);
        }

        public static void write(final File file, final Object value) throws IOException {
            OBJECT_MAPPER.writeValue(file, value);
        }

        private static URLConnection openConnection(URL url) throws IOException {
            final URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT);
            urlConnection.setReadTimeout(DEFAULT_READ_TIMEOUT);
            urlConnection.setAllowUserInteraction(false);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(false);
            return urlConnection;
        }

}