package ver1;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.jsoup.select.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.parser.StreamParser;

public class demo {
    public static void main(String[] args) {
        // String html = "<html><head><title>First parse</title></head>" + "<body><p>Parsed HTML into a doc.</p></body></html>";
        // Document doc = Jsoup.parse(html);
        // System.out.println(doc);

        // String html = "<div><p>Lorem ipsum.</p>";
        // Document doc = Jsoup.parseBodyFragment(html);
        // Element body = doc.body();
        // System.out.println(body);
        
        // HTML書き出し
        // try{
            // Document doc = Jsoup.connect("https://www.rakuten.co.jp/").get();
        //     String htmlString = doc.outerHtml();
        //     BufferedWriter bw = new BufferedWriter(new FileWriter("rakuten.html"));
        //     bw.write(htmlString);
        //     bw.close();
        // }catch(Exception e){
        //     System.out.println(e);
        // }

        // try{
        //     File input = new File("C:\\Users\\kawa1\\sotsuken1\\crawler\\rakuten.html");
        //     Document doc = Jsoup.parse(input, "UTF-8", "https://www.rakuten.co.jp/");
        //     // System.out.println(doc);
        //     String htmlString = doc.outerHtml();
        //     BufferedWriter bw = new BufferedWriter(new FileWriter("rakuten2.html"));
        //     bw.write(htmlString);
        //     bw.close();
        // }catch(Exception e){
        //     System.out.println(e);
        // }

        // try (StreamParser streamer = Jsoup.connect("https://www.rakuten.co.jp/")
        //     .execute()
        //     .streamParser()) {
            
        //     Element el;
        //     while ((el = streamer.selectNext("a")) != null) {
        //         // Will include the children of <article>
        //         System.out.println("Processing : " + el.text());
        //         el.remove(); // Keep memory usage low by discarding processed elements
        //     }
        // }catch(Exception e){
        //     System.out.println(e);
        // }

        // ベースURIの理解
        // try{
        //     File input = new File("C:\\Users\\kawa1\\sotsuken1\\a id=link href=shopitem.html商品詳細a.html");
        //     // String imput = "<a id=\"link\" href=\"shop/item.html\">商品詳細</a>";
        //     // ベースURIを "https://example.com/jp/" と設定してパース
        //     Document doc = Jsoup.parse(input, "UTF-8", "https://example.com/jp/");
        //     Element link = doc.getElementById("link");
    
        //     // 普通に属性を取ると相対パスのまま
        //     System.out.println(link.attr("href")); 
        //     // 結果: shop/item.html
    
        //     // 「abs:」をつけるか absUrl() を使うと絶対パスになる
        //     System.out.println(link.attr("abs:href")); 
        //     // 結果: https://example.com/jp/shop/item.html
        // }catch(Exception e){
        //     System.out.println(e);
        // }
        
        // 抽出
        try{
            File input = new File("C:\\Users\\kawa1\\sotsuken1\\crawler\\data\\【楽天市場】Shopping is Entertainment! ： インターネット最大級の通信販売、通販オンラインショッピングコミュニティ\\develop_js_1.1.2.min.css");
            Document doc = Jsoup.parse(input, "UTF-8", "https://www.rakuten.co.jp/");

            Elements content = doc.select("url");
            // Elements links = content.getElementsByTag("a");
            for (Element link : content) {
                // String linkHref = link.attr("href");
                System.out.println(link);
            }
        }catch(Exception e){
            System.out.println(e);
        }

        
        // try{
        //     File input = new File("C:\\Users\\kawa1\\sotsuken1\\crawler\\rakuten.html");
        //     Document doc = Jsoup.parse(input, "UTF-8", "https://www.rakuten.co.jp/");
    
        //     Elements links = doc.select("a[href]"); // a with href
        //     Elements pngs = doc.select("img[src$=.png]");
        //     // img with src ending .png
    
        //     Element masthead = doc.select("div.masthead").first();
        //     // div with class=masthead
    
        //     Elements resultDivs = doc.select("h3.r > div");
        //     // direct div after h3
        //     Elements resultAs   = resultDivs.select("a");
        //     // A elements within resultDivs
        //     System.out.println(pngs);
        // }catch(Exception e){
        //     System.out.println(e);
        // }

        
        // try{
        //     Document doc = Jsoup.connect("https://jsoup.org/").get();
            
        //     Elements elements = doc.selectXpath("//div[@class='col1']/p");
        //         // Each P element in div.col1
            
        //     List<TextNode> textNodes = doc.selectXpath("//a/text()", TextNode.class);
        //         // Each TextNode in every A element
        //     System.out.println(textNodes);

        // }catch(Exception e){
        //     System.out.println(e);
        // }

        // try{
        //     String html = "<p>An <a href='https://example.com/'><b>example</b></a> link.</p>";
        //     Document doc = Jsoup.parse(html);
        //     Element link = doc.select("a").first();

        //     String text = doc.body().text(); // "An example link"
        //     String linkHref = link.attr("href"); // "https://example.com/"
        //     String linkText = link.text(); // "example""
        //     String linkInnerH = link.html(); // "<b>example</b>"
        //     String linkOuterH = link.outerHtml(); 
        //         // "<a href="https://example.com"><b>example</b></a>"
        //     System.out.println(linkHref);
        // }catch(Exception e){
        //     System.out.println(e);
        // }

        // try{
        //     Document doc = Jsoup.connect("https://jsoup.org").get();
    
        //     Element link = doc.select("a").first();
        //     String relHref = link.attr("href"); // == "/"
        //     String absHref = link.attr("abs:href"); // "https://jsoup.org/"
        //     System.out.println(relHref);
        //     // System.out.println(absHref);
        // }catch(Exception e){
        //     System.out.println(e);
        // }

        // try{

        // }catch(Exception e){
        //     System.out.println(e);
        // }
    }
}