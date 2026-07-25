package ver3.finder;

import java.nio.file.Path;
import java.util.HashMap;

public interface ResourceFinder {
    void find(String url, HashMap<String, Path> resourceMap);
}
