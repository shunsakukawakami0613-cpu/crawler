package ver3.saveDocuments.replacer;

public class ImgReplacer implements ResourceReplacer{
    
    @Override
    public String getQuery() {
        return "img[src]";
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
