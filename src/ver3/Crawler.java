package ver3;

import org.jsoup.nodes.Document;

import ver2.finder.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Crawler {

    // resourceを保存するパス
    Path resourceFolderPath;

    // linkを保存するパス
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
        
        // link, resourceを探す
        find(url, currentDepth);

        // resourceをダウンロード
        downloadResources(linkMap, resourceFolderPath);

        // linkを保存
        saveDocuments(linkMap, htmlFolderPath);

    }

    

    private void find(String url, int currentDepth){

        putLinkMap(url, htmlFolderPath, linkMap);
        
        // img, css, js を探す
        findResources(url, resourceMap);

        // linkを探してくる
        List<String> nextLinks = findLinks(url);

        // 見つけたlinkをクロール
        int nextDepth = currentDepth + 1;
        // 深度が残っているか判定
        if(nextDepth <= maxDepth){
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
                crawl(link, nextDepth);
            }
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

    private void downloadResources(HashMap<String, Path> linkMap, Path resourceFolderPath){
        ResourceDownloader resourceDownloader = new ResourceDownloader();
        resourceDownloader.download(linkMap, resourceFolderPath);
    }


    private void saveDocuments(HashMap<String, Path> linkMap, Path linkFolderPath){
        DocSaver docSaver = new DocSaver();
        docSaver.save(linkMap, linkFolderPath);
    }

}
