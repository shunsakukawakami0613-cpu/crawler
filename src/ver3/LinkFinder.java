package ver3;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ver3.finder.*;

public class LinkFinder {

    int maxDepth;
    Path htmlFolderPath;
    Path resourceFolderPath;
    HashMap<String, Path> linkMap;
    HashMap<String, Path> resourceMap;
    Queue<String> linkQueue;
    
    LinkFinder(int maxDepth, Path htmlFolderPath, HashMap<String, Path> linkMap, HashMap<String, Path> resourceMap){
        this.maxDepth = maxDepth;
        this.htmlFolderPath = htmlFolderPath;
        this.linkMap = linkMap;
        this.resourceMap = resourceMap;
    }
    
    public void find(String targetUrl){
        linkQueue = new LinkedList<String>();

        linkQueue.add(targetUrl);

        int currentDepth = 1;

        while(!linkQueue.isEmpty()){

            // キューから取得
            String url = linkQueue.poll();

            // linkMapに追加
            putLinkMap(url);
            
            // img, css, js を探す
            findResources(url);
    
            // 深さが残っているなら次のlinkを探す
            if(currentDepth < maxDepth){
                ++currentDepth;
                
                // 待機
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                // linkを探して、キューに追加
                putLinkToQueue(findNextLinks(url));
            }
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

    private List<String> findNextLinks(String url){
        NextLinkFinder nextLinkFinder = new NextLinkFinder();
        return nextLinkFinder.find(url);
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
}
