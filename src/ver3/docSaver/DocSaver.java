package ver3.docSaver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

import org.jsoup.nodes.Document;

import ver3.util.DocMaker;

public class DocSaver {
    public void save(Map<String, Path> linkMap, Map<String, Path> resourceMap, Path folderPath) {
        Set<String> links = linkMap.keySet();
        for(String link : links){

            DocMaker docMaker = new DocMaker();
            Document doc = docMaker.make(link);

            ReplaceDoc(doc, linkMap, resourceMap);

            saveDoc(doc, linkMap.get(link));
        }
    }
    
    private void ReplaceDoc(Document doc, Map<String, Path> linkMap, Map<String, Path> resourceMap){
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
