package ver2;

public class ManageUrl {
    
    String url;

    ManageUrl(String url){
        this.url = url;
    }

     //  URLからファイル名を取得し，そのファイル名を返す
    public String makeFileName(){
        String fileName = removeQuestion().url;
        int index = fileName.lastIndexOf("/");
        if(index >= 0){
            fileName = fileName.substring(index + 1);
            if(fileName.length() == 0){
                fileName = fileName.substring(0, fileName.length() - 1);
                return makeFileName();
            }
        }
        // fileName = removeCannotUseWord(fileName);
        return fileName;
    }
    
    // URLからフォルダ名を取得し，そのフォルダ名を返す
    public String makeFolderName(){
       String folderName = url;
       int index = url.indexOf("/");
       if(index >= 0){
           folderName = url.substring(0, index);
           if(folderName.length() == 0){
               url = url.substring(1);
               return makeFolderName();
           }
       }
    //    folderName = removeCannotUseWord(folderName);
       return folderName;
   }

    // URLから"?"を取り除く
    public ManageUrl removeQuestion(){
        String result = url;
        int index = url.lastIndexOf("?");
        if(index >= 0){
            result = url.substring(0, index);
        }
        return new ManageUrl(result);
     }

    //  URLから特定の文字列を取り除く
    public ManageUrl removeString(String stringToRemove){
        String result = url;
        int index = url.indexOf(stringToRemove);
        if(index >= 0){
            result = url.substring(index + stringToRemove.length());
        }
        return new ManageUrl(result);
     }

    //  URLから特定の文字列を取り除く
     public ManageUrl removeLastString(String stringToRemove){
        String result = url;
        int index = url.lastIndexOf(stringToRemove);
        if(index >= 0){
            result = url.substring(0, index);
        }
        return new ManageUrl(result);
     }

     //  URLからファイル名やフォルダ名に使えない文字を取り除く
     public String removeCannotUseWord(String string){
        String result = string;
        String[] cannotUseWords = {"<", ">", ":", "\"", "/", "\\", "|", "?", "*"};
        for(String word : cannotUseWords){
            result = result.replace(word, " ");
        }
        return result;
     }
}
