package ver3;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ver3.docSaver.DocSaver;
import ver3.linkFinder.LinkFinder;
import ver3.resourceDownloader.ResourceDownloader;

public class Crawler {

    // ターゲットURL
    String targetUrl;
    
    // 最大深度
    int maxDepth;
    
    // resourceを保存するフォルダのパス
    Path resourceFolderPath;

    // htmlを保存するフォルダのパス
    Path htmlFolderPath;

    // linkを保存するマップ
    Map<String, Path> linkMap = new ConcurrentHashMap<String, Path>();

    // resourceを保存するマップ
    Map<String, Path> resourceMap = new ConcurrentHashMap<String, Path>();


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
        LinkFinder linkFinder = new LinkFinder(maxDepth, htmlFolderPath, linkMap, resourceMap);
        linkFinder.find(targetUrl);
    }

    private void downloadResources(){
        ResourceDownloader resourceDownloader = new ResourceDownloader();
        resourceDownloader.download(resourceMap, resourceFolderPath);
    }

    private void saveDocuments(){
        DocSaver docSaver = new DocSaver();
        docSaver.save(linkMap, resourceMap, htmlFolderPath);
    }

}
