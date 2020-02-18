package webcrawler;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.util.*;

public class Pipeline 
{
	
	private static Properties properties;
	private static String propertiesName = "tokenize, ssplit, pos, lemma, ner, parse, sentiment";
	public static StanfordCoreNLP stanfordCoreNLP;
	
	private Pipeline() {}
	
	static 
	{
		properties = new Properties();
		properties.setProperty("annotators", propertiesName);
	}
	
	public static StanfordCoreNLP getPipeline()
	{
		if(stanfordCoreNLP == null)
		{
			stanfordCoreNLP = new StanfordCoreNLP(properties);
		}
		return stanfordCoreNLP;

	}

}


// references
// https://www.youtube.com/watch?v=zjop7sE3g8I
