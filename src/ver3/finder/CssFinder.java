package ver3.finder;

import java.nio.file.Path;
import java.util.HashMap;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ver3.DocMaker;

public class CssFinder implements ResourceFinder{

    @Override
    public void find(String url, HashMap<String, Path> resourceMap) {

        DocMaker docMaker = new DocMaker();
        Document doc = docMaker.make(url);

        Elements links = doc.select("link[href~=.css]");
        for(Element element : links){
            resourceMap.put(element.attr("abs:href"), null);
            System.out.println("find: " + element.attr("abs:href"));
        }
    }
    
}
