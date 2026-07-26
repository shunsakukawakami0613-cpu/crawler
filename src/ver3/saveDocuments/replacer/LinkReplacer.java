package ver3.saveDocuments.replacer;

import java.nio.file.Path;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkReplacer {
    public void replaceLink(Document doc, Map<String, Path> linkMap){
        Elements links = doc.select("a[href]");
        for(Element element : links){
            String url = element.attr("abs:href");
            if(linkMap.containsKey(url) && linkMap.get(url) != null){
                element.attr("href", linkMap.get(url).toString());
            }
        }
    }
}
