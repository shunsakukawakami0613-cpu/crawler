package ver3.resourceDownloader;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ResourceDownloader {

    // 同時ダウンロードするスレッド数（例: 5~10程度）
    private static final int THREAD_COUNT = 7;

    public void download(Map<String, Path> resourceMap, Path path) {
        
        // スレッドプールの作成
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        Set<String> resourceUrls = resourceMap.keySet();
        
        for (String resourceUrl : resourceUrls) {
            // 各タスクをスレッドプールに投入
            executor.submit(() -> {
                UrlDownloader downloadFromUrl = new UrlDownloader(resourceUrl, path);
                Path downloadedPath = downloadFromUrl.download();
                if (downloadedPath != null) {
                    resourceMap.put(resourceUrl, downloadedPath);
                }
            });
        }

        // 全タスクの追加終了を通知し、すべての処理が終わるのを待つ
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
}