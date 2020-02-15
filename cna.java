package webcrawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class cna extends WebcrawlerTest{
	private String article_keyword, ptag_keyword;
	
	
	public cna(String website, int depth, HashSet<String> linkStrings,List<String[]> articles) {
		super(website, depth, linkStrings, articles);
		this.article_keyword = "coronavirus";
		this.ptag_keyword = "cases";
	}
    public void getHeaders(String article_keyword, String ptag_keyword) {
        this.linkStrings.forEach(x -> {
        	//System.out.println(x);
        	String test = "";
            Document document;
            try {
                document = Jsoup.connect(x).get();
                Elements articleLinks = document.select("h1");
                Elements date = document.select("time");
                Elements ptag = document.select("p");
                for (Element article : articleLinks) {
                    if (article.text().contains(this.article_keyword)) {
                    	if(test != x) { 
	                        ArrayList<String[]> temporary = new ArrayList<>();
	                        System.out.println(x);
	                        System.out.println("matching article " + article.text());
	                    	
	                    	if(ptag.text().contains(this.ptag_keyword)) {
	                    		System.out.println("cases match");
	                    		articles.add(new String[] {date.text() , article.text(), ptag.text(), x});
	                    		test = x;
	                    	}
	                    	else {
	                    		temporary.add(new String[] {"no match found in article"});
	                    		test = x;
	                    	}
	                        //System.out.println(article.attr("h1"));
	                        //temporary.add(new String[] {article.text()}); //The title of the article
	                        //temporary.add(new String[] {x}); //The URL of the article
	                        //temporary.add(new String[] {date.text()});
                    	}
                    	else {
                    		System.out.println("discarded");
                    	}
                    }
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        });
    }
    
  /*  public void writeToFile(String filename) {
        FileWriter writer;
        try {
            writer = new FileWriter(filename);
            articles.forEach(a -> {
                try {
                    String temp ="- Date: " + a.get(3) + "- Title: " + a.get(1) + "- cases: " + a.get(0) + " (link: " + a.get(2) + ")\n";
                    //display to console
                    //System.out.println(temp);
                    //save to file
                    //this.givenDataArray_whenConvertToCSV_thenOutputCreated();
                    writer.write(temp);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            });
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }*/
	
}
