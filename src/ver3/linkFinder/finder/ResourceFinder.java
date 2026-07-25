package ver3.linkFinder.finder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface ResourceFinder {

    String getQuery();
    String getAttr();

    default void find(Document doc, Map<String, Path> resourceMap){

        Elements links = doc.select(getQuery());
        for(Element element : links){
            String attrValue = element.attr(getAttr());
            resourceMap.put(attrValue, Paths.get(""));
            System.out.println("find: " + attrValue);
        }
    }
}
