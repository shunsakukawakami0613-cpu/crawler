package ver3.finder;

import java.nio.file.Path;
import java.util.HashMap;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ver3.DocMaker;

public class JsFinder implements ResourceFinder {
    
    @Override
    public void find(String url, HashMap<String, Path> resourceMap) {

        DocMaker docMaker = new DocMaker();
        Document doc = docMaker.make(url);

        Elements links = doc.select("script[src]");
        for(Element element : links){
            resourceMap.put(element.attr("abs:src"), null);
            System.out.println("find: " + element.attr("abs:src"));
        }
    }
    
}
