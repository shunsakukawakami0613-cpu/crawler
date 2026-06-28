package ver1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

public class CrawlerMain {
    static String URL = "https://www.rakuten.co.jp/";
    // static String URL = "http://news.ycombinator.com/";
    static String path = "C:\\Users\\kawa1\\sotsuken1\\crawler\\data\\";
    
    public static void main(String[] args){
        try{
            Document doc = Jsoup.connect(URL).get();
            
            Crawler crawler = new Crawler(doc, path);
            crawler.crawl();

        }catch(Exception e){
            System.out.println(e);
        }
    }
}
