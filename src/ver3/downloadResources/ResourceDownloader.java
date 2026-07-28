package ver3.downloadResources;

import java.nio.file.Path;
import java.util.Map;

import ver3.util.ParallelSetExecutor;

public class ResourceDownloader {

    // 同時ダウンロードするスレッド数（例: 5~10程度）
    private static final int THREAD_COUNT = 10;
    
    public void download(Map<String, Path> resourceMap, Path resourceFolderPath, Path htmlFolderPath) {
        
        ParallelSetExecutor.execute(resourceMap.keySet(), url -> {
            downloadProcess(url, resourceMap, resourceFolderPath, htmlFolderPath);
        }, THREAD_COUNT);

    }
    

    private void downloadProcess(String resourceUrl, Map<String, Path> resourceMap, Path resourceFolderPath, Path htmlFolderPath) {
        UrlDownloader urlDownloader = new UrlDownloader();
        Path downloadedPath = urlDownloader.download(resourceUrl, resourceFolderPath);
        if (downloadedPath != null) {
            Path relativePath = htmlFolderPath.relativize(downloadedPath);
            resourceMap.put(resourceUrl, relativePath);
        }
    }
}