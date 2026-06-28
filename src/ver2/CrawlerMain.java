package ver2;

public class CrawlerMain {
    // static String URL = "https://www.rakuten.co.jp/";
    static String URL = "https://books.toscrape.com/";
    // static String URL = "http://news.ycombinator.com/";
    static String path = "C:\\Users\\kawa1\\sotsuken1\\crawler\\data\\";
    static int depth = 2;
    
    public static void main(String[] args){
        
        try{
            // 保存するフォルダを作り、作ったフォルダのパスを取得する
            MakeFolder makeFolder = new MakeFolder();
            String folderpath = makeFolder.make(URL, path);

            // srcフォルダを作成
            makeFolder.make("src", folderpath);

            // クロール
            Crawler crawler = new Crawler(URL, depth, folderpath);
            crawler.crawl();

        }catch(Exception e){
            System.out.println(e);
        }

    }

}
