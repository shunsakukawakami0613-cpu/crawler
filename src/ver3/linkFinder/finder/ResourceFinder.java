package ver3.linkFinder.finder;

import java.nio.file.Path;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ver3.util.DocMaker;

public interface ResourceFinder {

    String getQuery();
    String getAttr();

    default void find(String url, Map<String, Path> resourceMap){
        DocMaker docMaker = new DocMaker();
        Document doc = docMaker.make(url);

        Elements links = doc.select(getQuery());
        for(Element element : links){
            String attrValue = element.attr(getAttr());
            resourceMap.put(attrValue, null);
            System.out.println("find: " + attrValue);
        }
    }
}
