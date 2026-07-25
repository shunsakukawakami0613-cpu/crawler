package ver3;

public class CrawlerMain {
    
    static String targetUrl = "https://www.rakuten.co.jp/";
    // static String downloadUrl = "https://books.toscrape.com/";
    
    static int currentDepth = 1;
    static int maxDepth = 1;
    
    public static void main(String[] args){
        // クロール
        if(currentDepth <= maxDepth){
            Crawler crawler = new Crawler(targetUrl, maxDepth);
            crawler.crawl();
        }
    }
}
