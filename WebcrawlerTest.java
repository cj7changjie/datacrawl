package webcrawler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.File;
import java.io.FileWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class WebcrawlerTest {

    //private static final int MAX_DEPTH = 3;
    protected static HashSet<String> linkStrings = new HashSet<>();
    protected static List<String[]> articles = new ArrayList<>();
    private String targetSite;
    private int crawldepth;
    
    
    public WebcrawlerTest(String website, int depth, HashSet<String> listOfWebsites, List<String[]> articles){
    	targetSite = website;
    	crawldepth = depth;
    	linkStrings = listOfWebsites;
    	articles = articles;
    }
    
    public static void main(String[] args) {
    	String url = "https://www.channelnewsasia.com/";
    	int searchdepth = 20;
    	cna channelnewsasia = new cna(url,searchdepth,linkStrings,articles);
        
        try {
            channelnewsasia.getPageLinks(url,0);
            channelnewsasia.getHeaders("coronavirus", "cases");
            //channelnewsasia.writeToFile("cna.txt");
            channelnewsasia.Output();
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
    public void getPageLinks(String URL, int initialdepth) {
        if ((!linkStrings.contains(URL) && (initialdepth < this.crawldepth))) {
            //System.out.println(">> Depth: " + depth + " [" + URL + "]");
            try {
            	if(URL.contains(this.targetSite) && !(URL.contains("#"))) {
            		 System.out.println(">>Depth: " + initialdepth + " [" + URL + "]");
	            	linkStrings.add(URL);
	
	                Document document = Jsoup.connect(URL).get();
	                Elements linksOnPage = document.select("a[href]");
	
	                initialdepth++;
	                for (Element page : linksOnPage) {
	                    getPageLinks(page.attr("abs:href"), initialdepth);
	                }
            	}
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }
    
    public String convertToCSV(String[] data) {
        return Stream.of(data)
          .map(this::escapeSpecialCharacters)
          .collect(Collectors.joining(","));
    }
    
    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
    

    public void Output() throws IOException {
        File csvOutputFile = new File("uguu.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            articles.stream().map(this::convertToCSV).forEach(pw::println);
        }
    }
    
}

