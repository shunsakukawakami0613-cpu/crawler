package ver3;

import java.nio.file.Path;
import java.nio.file.Paths;

import ver3.util.FolderMaker;

public class CrawlerInit {
    
    String targetUrl;
    Path folderPath;
    Path resourceFolderPath;
    Path linkFolderPath;

    
    public void init(String targetUrl){
        this.folderPath = makeCrawlFolderPath(targetUrl);
        this.linkFolderPath = makeHtmlFolderPath(folderPath);
        this.resourceFolderPath = makeResourceFolderPath(folderPath);
    }


    // downloadフォルダの作成
    private static Path makeCrawlFolderPath(String targetUrl){
        // targetUrlをフォルダ名に使用する
        String replacedTargetUrl = targetUrl.replaceAll("[\\\\/:*?\"<>|]", "_");
        // カレントリのパス
        Path currentPath = Paths.get("");
        // フォルダのパスを作成
        Path folderPath = currentPath.resolve("download").resolve(replacedTargetUrl);
        // フォルダ作成
        FolderMaker folderMaker = new FolderMaker();
        folderMaker.make(folderPath);
        return folderPath;
    }

    // htmlフォルダの作成
    private static Path makeHtmlFolderPath(Path folderPath){
        FolderMaker folderMaker = new FolderMaker();
        Path linkFolderPath = folderPath.resolve("html");
        folderMaker.make(linkFolderPath);
        return linkFolderPath;
    }

    // resourceフォルダの作成
    private static Path makeResourceFolderPath(Path folderPath){
        FolderMaker folderMaker = new FolderMaker();
        Path resourceFolderPath = folderPath.resolve("resource");
        folderMaker.make(resourceFolderPath);
        return resourceFolderPath;
    }
}
