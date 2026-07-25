package ver3.linkFinder.finder;

public class ImgFinder implements ResourceFinder{
    
    @Override
    public String getQuery() {
        return "img[src]";
    }
    
    @Override
    public String getAttr() {
        return "abs:src";
    }
}