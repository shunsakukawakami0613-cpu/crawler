package ver3;

public class CrawlerMain {
    
    static String targetUrl = "https://www.rakuten.co.jp/";
    // static String targetUrl = "https://books.toscrape.com/";
    
    static int maxDepth = 2;
    
    public static void main(String[] args){
        // クロール
        if(maxDepth > 0){
            Crawler crawler = new Crawler(targetUrl, maxDepth);
            crawler.crawl();
        }
    }
}
