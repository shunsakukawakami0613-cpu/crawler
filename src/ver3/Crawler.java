package ver3;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ver3.downloadResources.ResourceDownloader;
import ver3.findLinks.LinkFinder;
import ver3.saveDocuments.DocSaver;

public class Crawler {

    // ターゲットURL
    String targetUrl;
    
    // 最大深度
    int maxDepth;
    
    // htmlを保存するフォルダのパス
    Path htmlFolderPath;

    // resourceを保存するフォルダのパス
    Path resourceFolderPath;

    // linkを保存するスレッドセーフなMap
    Map<String, Path> htmlMap = new ConcurrentHashMap<>();

    // resourceを保存するスレッドセーフなMap
    Map<String, Path> resourceMap = new ConcurrentHashMap<>();


    // コンストラクタ
    public Crawler(String targetUrl, int maxDepth) {
        
        // ターゲットURLの設定
        this.targetUrl = targetUrl;

        // 最大深度の設定
        this.maxDepth = maxDepth;

        initCrawler();
    }


    // クロールメソッド
    public void crawl() {

        // linkを探す
        findLinks();

        // resourceをダウンロード
        downloadResources();

        // htmlを保存
        saveDocuments();
    }


    private void initCrawler(){
        CrawlerInit crawlerInit = new CrawlerInit();
        crawlerInit.init(targetUrl);
        this.resourceFolderPath = crawlerInit.resourceFolderPath;
        this.htmlFolderPath = crawlerInit.linkFolderPath;
    }

    private void findLinks(){
        LinkFinder linkFinder = new LinkFinder(maxDepth, htmlFolderPath, resourceFolderPath, htmlMap, resourceMap);
        linkFinder.find(targetUrl);
    }

    private void downloadResources(){
        ResourceDownloader resourceDownloader = new ResourceDownloader();
        resourceDownloader.download(resourceMap, resourceFolderPath, htmlFolderPath);
    }

    private void saveDocuments(){
        DocSaver docSaver = new DocSaver();
        docSaver.save(htmlMap, resourceMap, htmlFolderPath);
    }

}
