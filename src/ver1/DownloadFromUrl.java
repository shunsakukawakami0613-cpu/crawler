package ver1;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class DownloadFromUrl {

    String url;
    String path;

    // コンストラクタ
    DownloadFromUrl(String url, String path){
        this.url = url;
        this.path = path;
    }

    // URLからダウンロードする
    public String download() {
        
        ManageUrl manageUrl = new ManageUrl();
        String fileName =  path + manageUrl.makeFileName(manageUrl.removeQuestion(url));
        try{
            InputStream is = new URL(url).openStream();
            OutputStream os = new FileOutputStream(fileName);
            int line;
            while((line = is.read()) != -1){
                os.write(line);
            }
            os.close();
            
            // ダウンロードしたURLを表示
            System.out.println(url);
            
        }catch(Exception e){
            System.out.println(e);
        }

        return fileName;
    }
}
