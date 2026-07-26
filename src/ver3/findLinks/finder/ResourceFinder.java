package ver3.findLinks.finder;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface ResourceFinder {

    String getQuery();
    String getAttr();

    default Set<String> find(Document doc, Path htmlFolderPath, Path resourceFolderPath){

        Set<String> resourceUrls = new HashSet<>();

        Elements links = doc.select(getQuery());
        for(Element element : links){
            String attrValue = element.attr(getAttr());
            resourceUrls.add(attrValue);
            System.out.println("find: " + attrValue);
        }

        return resourceUrls;
    }
}
