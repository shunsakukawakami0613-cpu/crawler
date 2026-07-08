package ver1;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.jsoup.nodes.Document;

public class Crawler {
    
    Document doc;
    String path;

    // コンストラクタ
    Crawler(Document doc, String path){
        this.doc = doc;
        this.path = path;
    }

    // クロールメソッド(ダウンロード先のフォルダを作成，imgのダウンロード，cssのダウンロード，htmlのダウンロード)
    public void crawl() {
        
        try{
            // 保存するフォルダを作り、作ったフォルダのパスを取得する
            MakeFolder makeFolder = new MakeFolder();
            String folderpath = makeFolder.make(doc.title(), path);
            
            // html内のimgをダウンロード
            ImgDownloader imgDownloader = new ImgDownloader(doc, folderpath);
            imgDownloader.download();
            
            // html内のcssをダウンロード
            CssDownloader cssDownloader = new CssDownloader(doc, folderpath);
            cssDownloader.download();

            // html内のjsをダウンロード
            JsDownloader jsDownloader = new JsDownloader(doc, folderpath);
            jsDownloader.download();
            
            // htmlを保存
            SaveDoc(folderpath);

        }catch(Exception e){
            System.out.println(e);
        }
    }

    // HTML書き出し
    private void SaveDoc(String folderpath){
        try{
            // htmlをStringに
            String htmlString = doc.outerHtml();
            
            // 書き出し
            BufferedWriter bw = new BufferedWriter(new FileWriter(folderpath + doc.title() + ".html"));
            bw.write(htmlString);
            bw.close();

        }catch(Exception e){
            System.out.println(e);
        }
    }    
}
