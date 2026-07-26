package ver3.findLinks.finder;

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