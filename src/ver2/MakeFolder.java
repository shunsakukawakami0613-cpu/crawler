package ver2;

import java.io.File;

public class MakeFolder {

    // ダウンロードしたファイルを保存するためのフォルダを作り，そのフォルダのパスを返す
    public String make(String folderName, String path) {
        
        folderName = folderName.replaceAll("[\\\\/:*?\"<>|]", "_");

        // 作成するフォルダのパス生成
        String folderPath = path + folderName;

        // フォルダ作成
        File file = new File(folderPath);
        if(!file.exists()){
            boolean result = file.mkdirs();
            if(result){
                System.out.println(folderName + "フォルダを作成しました");
            }else{
                System.out.println(folderPath);
                System.out.println("フォルダを作成できませんでした");
            }
        }

        // 作成したフォルダのパスを返す
        return folderPath + "\\";
    
    }
    
}
