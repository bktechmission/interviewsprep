package bundleloadercheck;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BundlePropertiesCollection {

    private final Map<String, BundleProperties> bundles;

    public BundlePropertiesCollection() {
        this(Collections.EMPTY_MAP);
    }

    @JsonCreator
    public BundlePropertiesCollection(@JsonProperty("bundles") final Map<String, BundleProperties> bundles) {
        this.bundles = new HashMap<>(bundles);
    }

    @JsonGetter("bundles")
    public Map<String, BundleProperties> getBundles() {
        return bundles;
    }

}

