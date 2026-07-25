package ver3.linkFinder.finder;

public class JsFinder implements ResourceFinder {
    
    @Override
    public String getQuery() {
        return "script[src]";
    }
    
    @Override
    public String getAttr() {
        return "abs:src";
    }
}
