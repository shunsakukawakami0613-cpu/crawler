package ver3.finder;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ver3.DocMaker;

public class NextLinkFinder {

    public List<String> find(String url) {
        
        DocMaker docMaker = new DocMaker();
        Document doc = docMaker.make(url);
        
        List<String> urls = new ArrayList<>();
        Elements links = doc.select("a[href]");
        for (Element element : links) {
            String absUrl = element.attr("abs:href");
            if (!absUrl.isEmpty()) {
                urls.add(absUrl);
                System.out.println("find: " + absUrl);
            }
        }
        return urls;
    }
}