package ver1;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SimpleMain {
    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("https://www.rakuten.co.jp").get();
            System.out.println("title is : " + doc.title());
        } catch (Exception e) {
            System.err.println("error" + e.getMessage());
        }
    }
}