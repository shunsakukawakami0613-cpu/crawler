package ver3.docSaver;

import java.nio.file.Path;
import java.util.Map;

import org.jsoup.nodes.Document;

import ver3.docSaver.replacer.*;

public class DocReplacer {
    
    public void replaceResource(Document doc, Map<String, Path> resourceMap){
        ResourceReplacer imgReplacer = new ImgReplacer();
        ResourceReplacer cssReplacer = new CssReplacer();
        ResourceReplacer jsReplacer = new JsReplacer();

        ResourceReplacer[] resourceReplacers = {imgReplacer, cssReplacer, jsReplacer};

        for(ResourceReplacer resourceReplacer : resourceReplacers){
            resourceReplacer.replaceResource(doc, resourceMap);
        }
    }

    public void replaceLink(Document doc, Map<String, Path> linkMap){
        LinkReplacer linkReplacer = new LinkReplacer();
        linkReplacer.replaceLink(doc, linkMap);
    }
}
