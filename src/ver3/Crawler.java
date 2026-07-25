package ver3;

import org.jsoup.nodes.Document;

import ver3.finder.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Crawler {

    // resourceを保存するフォルダのパス
    Path resourceFolderPath;

    // htmlを保存するフォルダのパス
    Path htmlFolderPath;

    // 最大深度
    int maxDepth;

    // linkを保存するキュー
    Queue<String> linkQueue = new ConcurrentLinkedQueue<String>();

    // docを書き換える際に使うマップ
    // link用 <url, そのurlから取得するファイルのパス>
    HashMap<String, Path> linkMap = new HashMap<String, Path>();
    // resource用 <url, そのurlから取得するファイルのパス>
    HashMap<String, Path> resourceMap = new HashMap<String, Path>();


    // コンストラクタ
    public Crawler(Path folderPath, int maxDepth) {
        
        FolderMaker folderMaker = new FolderMaker();
        
        // resourceフォルダの制作
        this.resourceFolderPath = folderPath.resolve("resource");
        folderMaker.make(resourceFolderPath);

        // htmlフォルダの制作
        this.htmlFolderPath = folderPath.resolve("html");
        folderMaker.make(htmlFolderPath);

        // 最大深度の設定
        this.maxDepth = maxDepth;
    }


    // クロールメソッド
    public void crawl(String url, int currentDepth) {
        
        // linkを探す
        findLinks(url, currentDepth);

        // resourceをダウンロード
        downloadResources(linkMap, resourceFolderPath);

        // htmlを保存
        saveDocuments(linkMap, htmlFolderPath);

    }



    private void findLinks(String url, int currentDepth){

        putLinkMap(url, htmlFolderPath, linkMap);
        
        // img, css, js を探す
        findResources(url, resourceMap);

        // linkを探して、キューに追加
        putLinkToQueue(findLinks(url));

        // 見つけたlinkをクロール
        int nextDepth = currentDepth + 1;
        // 深度が残っているか判定
        if(nextDepth <= maxDepth){
            return;
        }

        for(String link : nextLinks){
            
            // linkListに追加されている場合は戻る
            if(linkMap.containsKey(link)){
                continue;
            }

            // 待機
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // 次のクロール
            find(link, nextDepth);
        }
    }


    public void putLinkMap(String url, Path htmlFolderPath, HashMap<String, Path> linkMap){
        ReplaceCannotUseWord replaceCannotUseWord = new ReplaceCannotUseWord();
        String fileName = replaceCannotUseWord.replace(url)+".html";
        Path filePath = htmlFolderPath.resolve(fileName);
        linkMap.put(url, filePath);
    }

    private void findResources(String url, HashMap<String, Path> resourceMap){
        // img, css, js のURLを探す
        ResourceFinder imgFinder = new ImgFinder();
        ResourceFinder cssFinder = new CssFinder();
        ResourceFinder jsFinder = new JsFinder();

        ResourceFinder[] resourceFinders = {imgFinder, cssFinder, jsFinder};
        for(ResourceFinder resourceFinder : resourceFinders){
            resourceFinder.find(url, linkMap);
        }
    }

    private List<String> findLinks(String url){
        LinkFinder linkFinder = new LinkFinder();
        return linkFinder.find(url);
    }

    private void putLinkToQueue(List<String> nextLinks){
        // linkをキューに追加
        for(String link : nextLinks){
            if(link == null){
                continue;
            }

            if(linkQueue.contains(link)){
                continue;
            }
            
            if(linkMap.containsKey(link)){
                continue;
            }

            linkQueue.add(link);
        }
    }

    private void downloadResources(HashMap<String, Path> linkMap, Path resourceFolderPath){
        ResourceDownloader resourceDownloader = new ResourceDownloader();
        resourceDownloader.download(linkMap, resourceFolderPath);
    }


    private void saveDocuments(HashMap<String, Path> linkMap, Path linkFolderPath){
        DocSaver docSaver = new DocSaver();
        docSaver.save(linkMap, linkFolderPath);
    }

}
