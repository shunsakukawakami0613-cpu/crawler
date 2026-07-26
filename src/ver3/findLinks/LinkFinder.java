package ver3.findLinks;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jsoup.nodes.Document;

import ver3.findLinks.finder.*;
import ver3.util.DocMaker;
import ver3.util.QueueParallelExecutor;

public class LinkFinder {

    int maxDepth;
    Path htmlFolderPath;
    Path resourceFolderPath;
    Map<String, Path> htmlMap;
    Map<String, Path> resourceMap;

    // キューに積む構造体 <url, depth>
    private record Link(String url, int depth){}
    // キュー
    Queue<Link> linkQueue = new ConcurrentLinkedQueue<>();

    private static final int THREAD_COUNT = 10;
    

    public LinkFinder(int maxDepth, Path htmlFolderPath, Path resourceFolderPath, Map<String, Path> htmlMap, Map<String, Path> resourceMap){
        this.maxDepth = maxDepth;
        this.htmlFolderPath = htmlFolderPath;
        this.resourceFolderPath = resourceFolderPath;
        this.htmlMap = htmlMap;
        this.resourceMap = resourceMap;
    }
    

    public void find(String targetUrl){

        linkQueue.add(new Link(targetUrl, 1));

        QueueParallelExecutor.execute(linkQueue, link -> {
            findProcess(link);
        }, THREAD_COUNT);

    }
    

    private void findProcess(Link link){
        
        Document doc = docMake(link.url);

        // linkMapに追加
        putLinkMap(doc);

        // linkを探して、キューに追加
        Set<String> nextLinks = findNextLinks(doc, link.depth);
        if(nextLinks != null){
            addLinkQueue(nextLinks, link.depth);
        }

        // img, css, js を探す
        Set<String> resources = findResources(doc);
        if(resources != null){
            putResourceMap(resources);
        }
        
        // 待機
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void putLinkMap(Document doc){
        String fileName = doc.title().replaceAll("[\\\\/:*?\"<>|]", "_") + ".html";
        Path filePath = Paths.get("").resolve(fileName);
        htmlMap.put(doc.location(), filePath);
    }
    
    private Set<String> findNextLinks(Document doc, int depth){
        if(depth >= maxDepth){
            return null;
        }
        NextLinkFinder nextLinkFinder = new NextLinkFinder();
        return nextLinkFinder.find(doc);
    }

    private void addLinkQueue(Set<String> nextLinks, int depth){
        // linkをキューに追加
        for(String link : nextLinks){
            
            if(link == null){
                continue;
            }

            if(htmlMap.containsKey(link)){
                continue;
            }

            linkQueue.add(new Link(link, depth + 1));
        }
    }

    private Set<String> findResources(Document doc){
        // img, css, js のURLを探す
        ResourceFinder imgFinder = new ImgFinder();
        ResourceFinder cssFinder = new CssFinder();
        ResourceFinder jsFinder = new JsFinder();

        ResourceFinder[] resourceFinders = {imgFinder, cssFinder, jsFinder};

        Set<String> resources = new HashSet<>();

        for(ResourceFinder resourceFinder : resourceFinders){
            Set<String> resourceUrls = resourceFinder.find(doc, htmlFolderPath, resourceFolderPath);
            if(resourceUrls != null){
                resources.addAll(resourceUrls);
            }
        }

        return resources;
    }

    private void putResourceMap(Set<String> resources){
        for(String resource : resources){
            
            if(resource == null){
                continue;
            }

            if(resourceMap.containsKey(resource)){
                continue;
            }

            resourceMap.put(resource, Paths.get(""));
        }
    }

    private Document docMake(String url){
        DocMaker docMaker = new DocMaker();
        Document doc = docMaker.make(url);
        return doc;
    }

}
