package ver3.docSaver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;

import ver3.util.DocMaker;

public class DocSaver {

    private static final int THREAD_COUNT = 7;

    public void save(Map<String, Path> linkMap, Map<String, Path> resourceMap, Path folderPath) {
        
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        
        Set<String> links = linkMap.keySet();
        
        for(String link : links){

            executor.submit(() -> {
                DocMaker docMaker = new DocMaker();
                Document doc = docMaker.make(link);
    
                replaceDoc(doc, linkMap, resourceMap);
    
                saveDoc(doc, linkMap.get(link));
            });

        }

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
    
    private void replaceDoc(Document doc, Map<String, Path> linkMap, Map<String, Path> resourceMap){
        DocReplacer docReplacer = new DocReplacer();
        docReplacer.replaceResource(doc, resourceMap);
        docReplacer.replaceLink(doc, linkMap);
    }

    private void saveDoc(Document doc, Path filePath){
        // htmlをStringに
        String htmlString = doc.html();
        
        // 書き出し
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(filePath.toString()));
            bw.write(htmlString);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(filePath);
    }
}
