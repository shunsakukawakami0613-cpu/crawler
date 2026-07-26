package ver3.saveDocuments;

import java.nio.file.Path;
import java.util.Map;

import org.jsoup.nodes.Document;

import ver3.saveDocuments.replacer.*;

public class DocReplacer {
    
    public void replace(Document doc, Map<String, Path> resourceMap, Map<String, Path> linkMap){
        replaceResource(doc, resourceMap);
        replaceLink(doc, linkMap);
    }

    private void replaceResource(Document doc, Map<String, Path> resourceMap){
        ResourceReplacer imgReplacer = new ImgReplacer();
        ResourceReplacer cssReplacer = new CssReplacer();
        ResourceReplacer jsReplacer = new JsReplacer();

        ResourceReplacer[] resourceReplacers = {imgReplacer, cssReplacer, jsReplacer};

        for(ResourceReplacer resourceReplacer : resourceReplacers){
            resourceReplacer.replaceResource(doc, resourceMap);
        }
    }

    private void replaceLink(Document doc, Map<String, Path> linkMap){
        LinkReplacer linkReplacer = new LinkReplacer();
        linkReplacer.replaceLink(doc, linkMap);
    }
}
