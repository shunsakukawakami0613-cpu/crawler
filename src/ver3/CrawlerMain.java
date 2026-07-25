package ver3;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CrawlerMain {
    
    static String targetUrl = "https://www.rakuten.co.jp/";
    // static String downloadUrl = "https://books.toscrape.com/";
    
    static int currentDepth = 1;
    static int maxDepth = 1;
    
    public static void main(String[] args){
        
        // クロール用の保存フォルダを指すパスの作成
        Path folderPath = makeCrawlFolderPath();
        
        // クロール
        if(currentDepth <= maxDepth){
            Crawler crawler = new Crawler(folderPath, maxDepth);
            crawler.crawl(targetUrl, currentDepth);
        }
    }
    

    // ダウンロードするフォルダの作成
    private static Path makeCrawlFolderPath(){
        
        // カレントリのパス
        Path currentPath = Paths.get("");

        // targetUrlをフォルダ名に使用する
        String replacedTargetUrl = targetUrl.replaceAll("[\\\\/:*?\"<>|]", "_");
        
        // 保存するフォルダのパスを作成
        return currentPath.resolve("download").resolve(replacedTargetUrl);
    }

}
