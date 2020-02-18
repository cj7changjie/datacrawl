package webcrawler;

import java.io.*;
import java.util.*;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class Sentiment {

	public static void main(String[] args) throws IOException 
	{
		StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
		
		// file name can be changed
		String fileName = "pastTweets.csv";
		File file_coro = new File(fileName);
		
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(file_coro));
		String line = "";
		
		// String line_sent = "";
		//CoreDocument coreDocument = new CoreDocument(line_sent);
		String str_sentiment = "";
		while ((line = br.readLine()) != null)
		{
			// split csv by comma without breaking sentence with comma java
			String[] count = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			//System.out.println(count[1] + ".");
			str_sentiment = str_sentiment + count[1] + "." + "\n";	
		}
		
		CoreDocument coreDocument1 = new CoreDocument(str_sentiment);
		// System.out.println(str_sentiment);
		
		stanfordCoreNLP.annotate(coreDocument1);
		List<CoreSentence> sentences = coreDocument1.sentences();
		for(CoreSentence sentence : sentences)
		{
			String sentiment = sentence.sentiment();
			System.out.println(sentiment + "\n" + sentence + "\n \n");
		}
		
	}

}


// references
// https://www.youtube.com/watch?v=zjop7sE3g8I
// https://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes
