package ver3.docSaver.replacer;

import java.nio.file.Path;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface ResourceReplacer {

    String getQuery();
    String getAttr();
    String getAbsAttr();

    default public void replaceResource(Document doc, Map<String, Path> resourceMap){
    Elements links = doc.select(getQuery());
        for(Element element : links){
            String url = element.attr(getAbsAttr());
            if(resourceMap.containsKey(url)){
                element.attr(getAttr(), resourceMap.get(url).toString());
            }
        }
    }
}