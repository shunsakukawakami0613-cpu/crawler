package ver1;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class CrawlerVer1_copy{
    
    static String URL = "https://www.rakuten.co.jp/";
    // static String URL = "https://www.yahoo.co.jp/";
    // static String URL = "http://news.ycombinator.com/";

    static String path = "C:\\Users\\kawa1\\sotsuken1\\crawler\\data\\";
    
    public static void main(String[] args){
        
        try{
            Document doc = Jsoup.connect(URL).get();
            crawl(doc, path);
            SaveDoc(doc, path, doc.title(), "html");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static void crawl(Document doc, String path) {
        try{
            path = MakeFolder(doc.title(), path);
            ImgDouwloader(doc, path);
            CssDownloader(doc, path);
            // LinkDownloader(doc, path);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static String DouwnloadFromUrl(String url, String path) {
        url = RemoveQuestion(url);
        String fileName = MakeFileName(url);
        String Name =  path + fileName;

        try{
            InputStream is = new URL(url).openStream();
            
            // デバッグ用
            // System.out.println(fileName);
            // 
            
            OutputStream os = new FileOutputStream(Name);
            int line;
            while((line = is.read()) != -1){
                os.write(line);
            }
            os.close();
            // src属性値の変更
            
        }catch(Exception e){
            System.out.println(e);
        }
        
        return Name;
    }
    
    private static void ImgDouwloader(Document doc, String path){
        Elements img = doc.select("img[src]");
        // 画像のダウンロード
        for(Element element : img){
            String url = element.attr("abs:src");
            String Name = DouwnloadFromUrl(url, path);
            element.attr("src", Name);
            
            // // 再起ダウンロード
            // String fileName = MakeFileName(url);
            // // System.out.println(fileName);
            // if(fileName.equals("/#IMAGEURL#")){
            //     File input = new File(path + fileName);
            //     System.out.println(input);
            //     try{
            //         Document nextDoc = Jsoup.parse(input, "UTF-8", path);
            //         crawl(nextDoc, Path);
            //     }catch(Exception e){
            //         System.out.println(e);
            //     }
            //     continue;
            // }
        }
    }
    
    private static void CssDownloader(Document doc, String path){
        Elements css = doc.select("link[href~=.css]");
        for(Element element : css){
            String url = element.attr("abs:href");
            String Name = DouwnloadFromUrl(url, path);
            // System.out.println(Name);
            element.attr("href", Name);
            
            // CssRewrite(Name, path);
        }
    }
    
    // public static void LinkDownloader(Document doc, String path){
    //     Elements link = doc.select("a[href]");
    //     System.out.println(link);
    //     for(Element element : link){
    //         String url = element.attr("abs:href");
    //         url = RemoveQuestio(url);
    //         DouwnloadFromUrl(element, "href", url, path);
    //     }
    // }

    private static void SaveDoc(Document doc, String path, String fileName, String format){
        // HTML書き出し
        try{
            String htmlString = doc.outerHtml();
            BufferedWriter bw = new BufferedWriter(new FileWriter(path + fileName + "." + format));
            bw.write(htmlString);
            bw.close();
            // System.out.println("aaa");
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static String MakeFolder(String folderName, String path) {
        // ダウンロードした画像を保存するための、URLのtitleと同じ名前のフォルダを作り、そのフォルダのパスを返す
        File file = new File(path + folderName);
        if(!file.exists()){
            boolean result = file.mkdirs();
            if(result){
                System.out.println("ファイルを作成しました");
            }else{
                System.out.println("ファイルを作成できませんでした");
            }
        }
        return path + folderName + "\\";
    }

    private static String RemoveQuestion(String url){
        String result = url;
        int index = url.indexOf("?");
        if(index >= 0){
            result = url.substring(0, index);
        }
        return result;
     }

     private static String MakeFileName(String url){
        String imgName = url;
            int index = url.indexOf("/");
            if(index >= 0){
                imgName = url.substring(url.lastIndexOf("/"));
            }
        return imgName;
     }

     private static void CssRewrite(String url, String path){
        String fileName = MakeFileName(url);
        try (BufferedReader br = new BufferedReader(new FileReader(path + fileName))) {
            String cssData = "";
            String line;
            while ((line = br.readLine()) != null) {
                cssData += line;
            }
            // デバック
            // System.out.println(cssData);
            // 

            //css内のurlからダウンロード 
            String pointer = cssData;
            while(true){
                int index = pointer.indexOf("url");
                if(index >= 0){
                    pointer = pointer.substring(index);
                    int startIndex = pointer.indexOf("(");
                    int endIndex = pointer.indexOf(")");
                    String newUrl_tmp = pointer.substring(startIndex + 1, endIndex);
                    String newUrl = newUrl_tmp.replaceAll("'", "");
                    // System.out.println(newUrl);
                    String Name = DouwnloadFromUrl(newUrl, path);
                    cssData = cssData.replace(newUrl_tmp, Name);
                    pointer = pointer.substring(endIndex);
                }else{
                    break;
                }
            }
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(path + fileName));
                bw.write(cssData);
                bw.close();
                // System.out.println(path + fileName + "を書き出しました");
            }catch(Exception e){
                System.out.println(e);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
     }
}
