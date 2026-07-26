package ver3.downloadResources;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.nio.file.Path;

import ver3.util.FolderMaker;

public class UrlDownloader {

    String removedUrl;

    String fileName;
    Path downloadPath;
    
    int index;

    // URLからダウンロードする
    public Path download(String url, Path path){
        
        if(url == null || url.isEmpty()){
            return null;
        }

        removedUrl = url;

        // http:// を除去
        if(url.startsWith("https://")){
            removedUrl = url.replace("https://", "");
        }

        // http:// を除去
        if(url.startsWith("http://")){
            removedUrl = url.replace("http://", "");
        }
        
        index = removedUrl.lastIndexOf("/");
        if(index >= 0){
            fileName = removedUrl.substring(index + 1);
            downloadPath = path.resolve(removedUrl.substring(0, index));
        }

        // URLの"/"ごとにフォルダを制作
        FolderMaker makeFolder = new FolderMaker();
        makeFolder.make(downloadPath);

        // ファイル名から"?"以降を除去
        index = fileName.indexOf("?");
        if(index >= 0){
            fileName = fileName.substring(0, index);
        }

        // ファイル名から拡張子を除去
        index = fileName.lastIndexOf(".");
        if(index >= 0){
            fileName = fileName.substring(0, index);
        }
        
        // コネクションを開く
        URLConnection con;
        try {
            con = new URL(url).openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        // 拡張子を取得
        String fileExtension = getFileExtension(con);
        
        // ファイル名を作成
        Path filepath =  downloadPath.resolve(fileName + fileExtension);
        
        // ダウンロード
        try (InputStream is = con.getInputStream();
            OutputStream os = new FileOutputStream(filepath.toString())) {
            is.transferTo(os);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        // ダウンロードしたURLを表示 確認用
        System.out.println("download: " + url);
        
        return filepath;
        

    }


    private String getFileExtension(URLConnection con){

        String contentsType = con.getContentType();
        
        String fileExtension = "";

        if(contentsType.contains("image/png")){
            fileExtension = ".png";
        }else if(contentsType.contains("image/jpeg")){
            fileExtension = ".jpg";
        }else if(contentsType.contains("image/gif")){
            fileExtension = ".gif";
        }else if(contentsType.contains("image/svg+xml")){
            fileExtension = ".svg";
        }else if(contentsType.contains("text/html")){
            fileExtension = ".html";
        }else if(contentsType.contains("text/css")){
            fileExtension = ".css";
        }else if(contentsType.contains("application/javascript")){
            fileExtension = ".js";
        }else if(contentsType.contains("application/x-javascript")){
            fileExtension = ".js";
        }else if(contentsType.contains("text/javascript")){
            fileExtension = ".js";
        }else if(contentsType.contains("application/ecmascript")){
            fileExtension = ".js";
        }else{
            fileExtension = "";
        }
        
        return fileExtension;
    }
}
