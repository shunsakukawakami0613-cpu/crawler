package ver1;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CssDownloader {
    
    Document doc;
    String folderpath;

    // コンストラクタ
    CssDownloader(Document doc, String folderpath){
        this.doc = doc;
        this.folderpath = folderpath;
    }

    // cssのダウンロード
    public void download(){
        Elements links = doc.select("link[href~=.css]");
        for(Element element : links){
            String url = element.attr("abs:href");
            
            // ダウンロードし、属性値の変更
            DownloadFromUrl downloadFromUrl = new DownloadFromUrl(url, folderpath);
            element.attr("href", downloadFromUrl.download());
        }
    }

}
