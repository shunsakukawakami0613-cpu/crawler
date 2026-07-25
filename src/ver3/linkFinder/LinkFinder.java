package ver3.linkFinder;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import ver3.linkFinder.finder.*;
import ver3.util.ReplaceCannotUseWord;

public class LinkFinder {

    
    int maxDepth;
    Path htmlFolderPath;
    Path resourceFolderPath;
    Map<String, Path> linkMap;
    Map<String, Path> resourceMap;

    // キューに積む構造体 <url, depth>
    private record Link(String url, int depth){}
    // キュー
    Queue<Link> linkQueue = new ConcurrentLinkedQueue<>();

    // フェーサー
    Phaser phaser = new Phaser(0);

    private static final int THREAD_COUNT = 5;
    
    public LinkFinder(int maxDepth, Path htmlFolderPath, Map<String, Path> linkMap, Map<String, Path> resourceMap){
        this.maxDepth = maxDepth;
        this.htmlFolderPath = htmlFolderPath;
        this.linkMap = linkMap;
        this.resourceMap = resourceMap;
    }
    
    public void find(String targetUrl){

        linkQueue.add(new Link(targetUrl, 1));

        phaser.register();

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        while(!linkQueue.isEmpty() && phaser.getRegisteredParties() > 0){
            // キューから取得
            Link link = linkQueue.poll();
            
            if(link == null){
                continue;
            }

            phaser.register();
            
            executor.submit(() -> {
                try{
                    findProcess(link);
                }finally{
                    phaser.arriveAndDeregister();
                }
            });
            
        }

        phaser.arriveAndDeregister();
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

    }
    
    private void findProcess(Link link){
        
        // linkMapに追加
        putLinkMap(link.url);
        
        // img, css, js を探す
        findResources(link.url);
        
        // 待機
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // linkを探して、キューに追加
        List<String> nextLinks = findNextLinks(link);
        if(nextLinks != null){
            putLinkToQueue(nextLinks, link.depth);
        }

    }

    private void putLinkMap(String url){
        ReplaceCannotUseWord replaceCannotUseWord = new ReplaceCannotUseWord();
        String fileName = replaceCannotUseWord.replace(url)+".html";
        Path filePath = htmlFolderPath.resolve(fileName);
        linkMap.put(url, filePath);
    }

    private void findResources(String url){
        // img, css, js のURLを探す
        ResourceFinder imgFinder = new ImgFinder();
        ResourceFinder cssFinder = new CssFinder();
        ResourceFinder jsFinder = new JsFinder();

        ResourceFinder[] resourceFinders = {imgFinder, cssFinder, jsFinder};
        for(ResourceFinder resourceFinder : resourceFinders){
            resourceFinder.find(url, resourceMap);
        }
    }

    private List<String> findNextLinks(Link link){
        if(link.depth > maxDepth){
            return null;
        }
        NextLinkFinder nextLinkFinder = new NextLinkFinder();
        return nextLinkFinder.find(link.url);
    }

    private void putLinkToQueue(List<String> nextLinks, int depth){
        // linkをキューに追加
        for(String link : nextLinks){
            if(link == null){
                continue;
            }

            if(linkMap.containsKey(link)){
                continue;
            }

            linkQueue.add(new Link(link, depth + 1));
        }
    }
}
