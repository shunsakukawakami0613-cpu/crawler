package ver3.docSaver.replacer;

public class JsReplacer implements ResourceReplacer{
    
    @Override
    public String getQuery() {
        return "script[src]";
    }
    
    @Override
    public String getAttr() {
        return "src";
    }

    @Override
    public String getAbsAttr() {
        return "abs:src";
    }
}
