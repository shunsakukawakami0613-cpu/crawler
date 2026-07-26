package ver3.findLinks.finder;

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
