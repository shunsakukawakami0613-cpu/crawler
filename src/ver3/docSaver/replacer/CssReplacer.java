package ver3.docSaver.replacer;

public class CssReplacer implements ResourceReplacer{

    @Override
    public String getQuery() {
        return "link[href~=.css]";
    }
    
    @Override
    public String getAttr() {
        return "href";
    }

    @Override
    public String getAbsAttr() {
        return "abs:href";
    }
}
