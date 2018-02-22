package bundleloadercheck;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class BundleProperties {

    private final String checksum;
    private final Map<String, List<String>> attributes;

    @JsonCreator
    public BundleProperties(@JsonProperty("checksum") final String checksum, @JsonProperty("attributes") Map<String, List<String>> attributes) {
        this.checksum = checksum;
        this.attributes = attributes;
    }

    public String getChecksum() {
        return checksum;
    }

    public Map<String, List<String>> getAttributes() {
        return attributes;
    }
}

