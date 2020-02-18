package TwitterCrawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import twitter4j.FilterQuery;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Streamer extends ConfigClass {
	
	
	protected ConfigurationBuilder config;
	protected ArrayList<Status> tweets;
	protected int TOTAL_TWEETS;
	protected String tweetsQuery;
	protected String[] tweetsMultiQuery;
	protected String fileName;
	
	public Streamer(String tweetsQuery, String[] tweetsMultiQuery,int TOTAL_TWEETS) {
		this.config = super.loadConfig();
		this.tweetsQuery = tweetsQuery;
		this.tweetsMultiQuery = tweetsMultiQuery;
		this.TOTAL_TWEETS = TOTAL_TWEETS;
	}
	
	
	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public List<Status> getPastTweets(){
		factory = new TwitterFactory(loadConfig().build());
		twitter = factory.getInstance();
		Query query = new Query(tweetsQuery);
		long lastID = Long.MAX_VALUE;
		tweets = new ArrayList<Status>();
		while (tweets.size() < TOTAL_TWEETS) {
			if (TOTAL_TWEETS - tweets.size() > 100)
				query.setCount(100);
			else
				query.setCount(TOTAL_TWEETS - tweets.size());
			try {
				QueryResult result = twitter.search(query);
				tweets.addAll(result.getTweets());
				System.out.println("Gathered " + tweets.size() + " tweets" + "\n");
				for (Status t : tweets)
					if (t.getId() < lastID)
						lastID = t.getId();
				try {
					processRTWithCount(tweets, getFileName());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return tweets; 
			}

			catch (TwitterException te) {
				System.out.println("Couldn't connect: " + te);
			};
			query.setMaxId(lastID - 1);
			
		}

		return tweets;
	}
	
	 public List<Status> streamTweets() throws TwitterException, InterruptedException {
	        // skipped for brevity...
		 	SimpleDateFormat myFormatObj = new SimpleDateFormat("dd MMM yyyy h:mmaa");
	        // TODO: You may have to tweak the capacity of the queue, depends on the filter query
	        final BlockingQueue<Status> statuses = new LinkedBlockingQueue<Status>(10000); 
	        TwitterStream twitterStream = new TwitterStreamFactory(loadConfig().build()).getInstance();
	        
	        StatusListener statusListener = new StatusListener() {

	            @Override
				public void onStatus(Status status) {
	                statuses.offer(status); // Add received status to the queue
	            }

				@Override
				public void onException(Exception arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onDeletionNotice(StatusDeletionNotice arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onScrubGeo(long arg0, long arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onStallWarning(StallWarning arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTrackLimitationNotice(int arg0) {
					// TODO Auto-generated method stub
					
				}

	            // etc...
	        };
	        
	        FilterQuery tweetFilterQuery = new FilterQuery(); // See
			tweetFilterQuery.track(tweetsMultiQuery); // OR on keywords
			twitterStream.addListener(statusListener);
			tweetFilterQuery.language(new String[] { "en" }); 
	    	twitterStream.filter(tweetFilterQuery);
	     
	        // Collect the 1000 statues
	        tweets = new ArrayList<Status>(TOTAL_TWEETS);
	        while (tweets.size() < TOTAL_TWEETS) {
	            // TODO: Handle InterruptedException
	            final Status status = statuses.poll(10, TimeUnit.SECONDS); 
	            
	            if (status == null) {
	                // TODO: Consider hitting this too often could indicate no further Tweets
	                continue;
	            }
	            tweets.add(status);
	            System.out.println("Live Streamer Gathered " + tweets.size() + " tweets" + "\n");
	        }
	     
	        
	        twitterStream.shutdown();
	        
	        try {
				processTweets(tweets, getFileName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        return tweets;
	    }
	 
	 	
	 
	 
	 	public void processTweets(ArrayList<Status> collected, String filename) throws IOException {
	 		Status status;
	 		String msg;
	 		String user;
	 		Date date;
	 		SimpleDateFormat strDateFormat = new SimpleDateFormat("dd MMM yyyy h:mmaa");
	 		
			for(int i = 0; i<collected.size(); i++) {
				status = collected.get(i);
				user = status.getUser().getScreenName();
				date = status.getCreatedAt();
				msg = removeHTMLinks(status.getText());
				if(msg.contains("RT") && user.isEmpty() == false) {
					msg = removeLineBreaks(msg);
					System.out.println(strDateFormat.format(date)+ " " + msg + " " + user + "\n");
					Output(strDateFormat.format(date),msg,user, filename);
				}
				
			}
	 	}
	 	
	 	public void processRTWithCount(ArrayList<Status> collected, String filename) throws IOException {
	 		Status status;
	 		String msg;
	 		String user;
	 		Date date;
	 		int rtCount;
	 		SimpleDateFormat strDateFormat = new SimpleDateFormat("dd MMM yyyy h:mmaa");
	 		
			for(int i = 0; i<collected.size(); i++) {
				status = collected.get(i);
				user = status.getUser().getScreenName();
				date = status.getCreatedAt();
				msg = removeHTMLinks(status.getText());
				rtCount = status.getRetweetCount();
				if(user.isEmpty() == false && rtCount > 0 ) {
					msg = removeLineBreaks(msg);
					System.out.println(strDateFormat.format(date)+ " " + msg + " " + user + " " + rtCount + "\n");
					OutputWithCount(strDateFormat.format(date),msg,user,rtCount, filename);
				}
				
			}
	 	}
	 	
	 	public String removeHTMLinks(String msg) {
	 		return msg = msg.replaceAll("(<a href[\\s\\S]?>[\\s\\S]?)|(\\b(http|https):\\/\\/.*[^ alt]\\b)", "");
	 	}
	 	
	 	public String removeLineBreaks(String msg) {
	 		return msg = msg.replaceAll("\\r|\\n|,", "");
	 	}
	 	
	 	public void Output(String date, String msg, String user, String filename) throws IOException {
	 	    	try (FileWriter f = new FileWriter(filename +".csv",true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b,true);) {
	 				StringBuffer csvData = new StringBuffer("");
	 				StringBuffer csvHeader = new StringBuffer(""); 
	 				csvHeader.append("Date/time,message,user,no_of_times_fav\n");
	 				csvData.append(date);
	 				csvData.append(',');
	 				csvData.append(msg);
	 				csvData.append(',');
	 				csvData.append(user);
	 				csvData.append('\n');
	 				p.write(csvData.toString());
	 			    p.flush();
	 			    p.close();
	 			     
	 			}
	 			catch (IOException e) {
	 				
	 				 e.printStackTrace();
	 			}

	 	}
	 	
	 	public void OutputWithCount(String date, String msg, String user, int favCount, String filename) throws IOException {
 	    	try (FileWriter f = new FileWriter(filename +".csv",true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b,true);) {
 				StringBuffer csvData = new StringBuffer("");
 				StringBuffer csvHeader = new StringBuffer(""); 
 				csvHeader.append("Date/time,message,user,no_of_times_fav\n");
 				csvData.append(date);
 				csvData.append(',');
 				csvData.append(msg);
 				csvData.append(',');
 				csvData.append(user);
 				csvData.append(',');
 				csvData.append(favCount);
 				csvData.append('\n');
 				p.write(csvData.toString());
 			    p.flush();
 			    p.close();
 			     
 			}
 			catch (IOException e) {
 				
 				 e.printStackTrace();
 			}

 	}
}
