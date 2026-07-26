package ver3.saveDocuments;

import java.nio.file.Path;
import java.util.Map;

import org.jsoup.nodes.Document;

import ver3.util.DocMaker;
import ver3.util.SetParallelExecutor;

public class DocSaver {

    private static final int THREAD_COUNT = 10;

    
    public void save(Map<String, Path> linkMap, Map<String, Path> resourceMap, Path htmlFolderPath) {
        
        SetParallelExecutor.execute(linkMap.keySet(), link -> {
            saveProcess(link, htmlFolderPath, resourceMap, linkMap);
        }, THREAD_COUNT);

    }
    
    
    private void saveProcess(String link, Path htmlFolderPath, Map<String, Path> resourceMap, Map<String, Path> linkMap){
    
        Document doc = makeDoc(link);
        
        replaceDoc(doc, linkMap, resourceMap);
        
        saveDoc(doc, htmlFolderPath.resolve(linkMap.get(link).toString()));
    }

    
    private Document makeDoc(String link){
        DocMaker docMaker = new DocMaker();
        return docMaker.make(link);
    }

    private void replaceDoc(Document doc, Map<String, Path> linkMap, Map<String, Path> resourceMap){
        DocReplacer docReplacer = new DocReplacer();
        docReplacer.replace(doc, resourceMap, linkMap);
    }


    private void saveDoc(Document doc, Path folderPath){
        HtmlWriter htmlWriter = new HtmlWriter();
        htmlWriter.writeHtml(doc, folderPath);
    }
}
