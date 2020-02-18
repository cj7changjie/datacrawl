package TwitterCrawler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import twitter4j.Status;
import twitter4j.TwitterException;

public class main {
	

	public static void main(String[] args) {
		
		String tweetsQuery = "#SINGAPORE";
		String[] tweetsMultiQuery = {"COVID_19", "SARS", "SINGAPORE", "WUHAN"};
		int noOfTweets = 5000;
		Streamer tweetStreamer = new Streamer(tweetsQuery, tweetsMultiQuery, noOfTweets);
		
		tweetStreamer.setFileName("pastTweets");
		tweetStreamer.getPastTweets();
		
		
		try {
			tweetStreamer.setFileName("liveTweets");
			tweetStreamer.streamTweets();
			
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
	}
	

}
