package ver1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ver1.DownloadFromUrl;
import ver1.ManageUrl;

public class CssDownloader_copy {
    // cssのダウンロード(相対パス)
    public void download(Document doc, String folderpath){
        Elements css = doc.select("link[href~=.css]");
        for(Element element : css){
            String url = element.attr("abs:href");
            DownloadFromUrl downloadFromUrl = new DownloadFromUrl();
            downloadFromUrl.download(url, folderpath);
            // 属性値の変更
            ManageUrl manageUrl = new ManageUrl(url);
            element.attr("href", manageUrl.makeFileName().substring(1));

            // cssRewrite(url, folderpath);
        }
    }



    private void cssRewrite(String url, String folderPath){
        ManageUrl manageUrl = new ManageUrl();
        String fileName = manageUrl.makeFileName(manageUrl.removeQuestion(url));
        try (BufferedReader br = new BufferedReader(new FileReader(folderPath + fileName))) {
            // System.out.println(fileName);
            String cssData = "";
            String line;
            while ((line = br.readLine()) != null) {
                cssData += line;
            }
            //css内のurlからダウンロード 
            String pointer = cssData;
            while(pointer.length() > 0){
                int index = pointer.indexOf("url");
                if(index >= 0){
                    pointer = pointer.substring(index);
                    int startIndex = pointer.indexOf("(");
                    int endIndex = pointer.indexOf(")");
                    String newUrl_tmp = pointer.substring(startIndex + 1, endIndex);
                    String newUrl = newUrl_tmp.replaceAll("'", "");
                    newUrl = newUrl.replaceAll("\"", "");
                    if(newUrl_tmp.equals("")){
                        pointer = pointer.substring(endIndex);
                        continue;
                    }
                    DownloadFromUrl downloadFromUrl = new DownloadFromUrl(newUrl, folderPath);
                    downloadFromUrl.download();
                    cssData = cssData.replace(newUrl_tmp, manageUrl.makeFileName().substring(1));
                    pointer = pointer.substring(endIndex);
                }else{
                    break;
                }
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(folderPath + fileName));
            bw.write(cssData);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
