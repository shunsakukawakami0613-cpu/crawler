package ver3.util;

public class ReplaceCannotUseWord{
    public String replace(String string) {
        return string.replaceAll("[\\\\/:*?\"<>|]", "_");
    }
}