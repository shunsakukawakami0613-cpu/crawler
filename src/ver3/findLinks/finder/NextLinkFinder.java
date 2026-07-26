package ver3.findLinks.finder;

import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NextLinkFinder {

    public Set<String> find(Document doc) {

        Set<String> urls = new HashSet<>();
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