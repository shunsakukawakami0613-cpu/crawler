package ver3.saveDocuments;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.jsoup.nodes.Document;

public class HtmlWriter {
    public void writeHtml(Document doc, Path filePath) {
        String htmlString = doc.html();
        
        try (BufferedWriter bw = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            bw.write(htmlString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("save: " + filePath);
    }
}
