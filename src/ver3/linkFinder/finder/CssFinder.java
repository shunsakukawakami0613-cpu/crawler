package ver3.linkFinder.finder;

public class CssFinder implements ResourceFinder{

    @Override
    public String getQuery() {
        return "link[href~=.css]";
    }

    @Override
    public String getAttr() {
        return "abs:href";
    }
    
}
