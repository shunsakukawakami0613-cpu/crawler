package ver1;

public class ManageUrl {

     //  URLからファイル名を取得し，そのファイル名を返す
    public String makeFileName(String url){
        String fileName = url;
        int index = url.lastIndexOf("/");
        if(index >= 0){
            fileName = url.substring(index + 1);
            if(fileName.length() == 0){
                url = url.substring(0, url.length() - 1);
                fileName = makeFileName(url);
            }
        }
        fileName = removeQuestion(fileName);
        return fileName;
    }

    // URLから"?"を取り除く
    public String removeQuestion(String url){
        String result = url;
        int index = url.lastIndexOf("?");
        if(index >= 0){
            result = url.substring(0, index);
        }
        return result;
     }
}
