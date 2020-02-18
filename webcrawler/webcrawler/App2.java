package webcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.io.FileWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class App2 {

    private static final int MAX_DEPTH = 4;
    private static int depth;
    private static HashSet<String> linkStrings = new HashSet<>();
    private static List<List<String>> articles = new ArrayList<>();
    
    public static void main(String[] args) {
        String url = "https://www.channelnewsasia.com/";
        try {
            getPageLinks(url,0);
            getHeaders();
            writeToFile("hullo.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts web crawling from url. Crawling is done in a breadth-first search
     * manner.
     * 
     * @param url the webpage url.
     * @throws IOException
     */
    public static void getPageLinks(String URL, int depth) {
        if ((!linkStrings.contains(URL) && (depth < MAX_DEPTH))) {
            //System.out.println(">> Depth: " + depth + " [" + URL + "]");
            try {
            	if(URL.contains("https://www.channelnewsasia.com") && !(URL.contains("#"))) {
            		 System.out.println(">>Depth: " + depth + " [" + URL + "]");
	            	linkStrings.add(URL);
	
	                Document document = Jsoup.connect(URL).get();
	                Elements linksOnPage = document.select("a[href]");
	
	                depth++;
	                for (Element page : linksOnPage) {
	                    getPageLinks(page.attr("abs:href"), depth);
	                }
            	}
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }
    
    public static void getHeaders() {
        linkStrings.forEach(x -> {
        	//System.out.println(x);
        	String test = "";
            Document document;
            try {
                document = Jsoup.connect(x).get();
                Elements articleLinks = document.select("h1");
                Elements date = document.select("time");
                Elements ptag = document.select("p");
                for (Element article : articleLinks) {
                    if (article.text().contains("Wuhan") ) {
                    	if(test != x) { 
	                        ArrayList<String> temporary = new ArrayList<>();
	                        System.out.println(x);
	                        System.out.println("matching article " + article.text());
	                    	
	                    	if(ptag.text().contains("cases")) {
	                    		System.out.println("cases match");
	                    		temporary.add(ptag.text());
	                    		test = x;
	                    	}
	                    	else {
	                    		temporary.add("no match found in article");
	                    		test = x;
	                    	}
	                        //System.out.println(article.attr("h1"));
	                        temporary.add(article.text()); //The title of the article
	                        temporary.add(x); //The URL of the article
	                        temporary.add(date.text());
	                        articles.add(temporary);
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
    
    public static void writeToFile(String filename) {
        FileWriter writer;
        try {
            writer = new FileWriter(filename);
            articles.forEach(a -> {
                try {
                    String temp ="- Date: " + a.get(3) + "- Title: " + a.get(1) + "- cases: " + a.get(0) + " (link: " + a.get(2) + ")\n";
                    //display to console
                    //System.out.println(temp);
                    //save to file
                    writer.write(temp);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            });
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
}
