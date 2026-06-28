package ver2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Crawler {
    
    // 変数
    int depth;
    Document doc;

    // static変数
    static String folderpath;
    static String srcpath;

    // URLを保存する静的なハッシュマップ
    static HashMap<String, String> urls = new HashMap<>();

    // 保存するhtmlのパス
    String docSavepath;



    // CrawlerMainからのみのコンストラクタ
    Crawler(String URL, int depth, String folderpath){
        try{
            this.doc = Jsoup.connect(URL).get();
            this.depth = depth;
            Crawler.folderpath = folderpath;
            Crawler.srcpath = folderpath + "src\\";
    
            // 保存するhtmlのファイル名
            String fileName = doc.title();
            // ファイル名に使えない文字を置換
            fileName = fileName.replaceAll("[\\\\/:*?\"<>|]", "_");
            // 保存するhtmlのパスを作成
            docSavepath = folderpath + fileName + ".html";
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    // 再起の際のコンストラクタ
    Crawler(String URL, int depth){
        try{
            this.doc = Jsoup.connect(URL).get();
            this.depth = depth;

            // 保存するhtmlのファイル名
            String fileName = doc.title();
            // ファイル名に使えない文字を置換
            fileName = fileName.replaceAll("[\\\\/:*?\"<>|]", "_");
            // 保存するhtmlのパスを作成
            docSavepath = folderpath + fileName + ".html";
        }catch(Exception e){
            System.out.println(e);
        }
    }
    


    // クロールメソッド
    public boolean crawl() {
        
        // mapに保存されている場合は戻る
        if(urls.containsKey(doc.location())){
            docSavepath = urls.get(doc.location());
            return true;
        }
        
        // depthが残っていないならクロールせずに戻る
        if(depth <= 0){
            return false;
        }

        System.out.println("title : " + doc.title());
        
        // mapに追加　<url, そのurlから保存するhtmlのパス>
        urls.put(doc.location(), docSavepath);

        // クロール開始
        try{
            Downloader downloader = new Downloader(doc, srcpath);
            
            // html内のimgをダウンロード
            downloader.imgdownload();
            
            // html内のcssをダウンロード
            downloader.cssdownload();
            
            // html内のjsをダウンロード
            downloader.jsdownload();
            
            // 再帰的にクロール
            RecursiveCrawler recursiveCrawler = new RecursiveCrawler(doc, depth);
            recursiveCrawler.recursiveCrawl();
            
            // htmlを保存
            SaveDoc();
            
        }catch(Exception e){
            System.out.println(e);
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(srcpath + "Exception.txt", true));
                bw.write(e + "\n");
                bw.close();
            }catch(Exception e2){
                System.out.println(e2);
            }
        }

        return true;
    }
    


    // HTML書き出し関数
    private void SaveDoc(){
        
        try{
            // htmlをStringに
            String htmlString = doc.html();
            
            // 書き出し
            BufferedWriter bw = new BufferedWriter(new FileWriter(docSavepath));
            bw.write(htmlString);
            bw.close();

        }catch(Exception e){
            System.out.println(e);
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(srcpath + "Exception.txt", true));
                bw.write(e + "\n");
                bw.close();
            }catch(Exception e2){
                System.out.println(e2);
            }
        }
    }

}
