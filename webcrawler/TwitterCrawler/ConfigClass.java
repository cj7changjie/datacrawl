package TwitterCrawler;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;


public class ConfigClass {
	protected ConfigurationBuilder config;
	protected static final String CONSUMER_KEYS = "tSVgdImcCbYebu2bzZjIHIEgo";
	protected static final String CONSUMER_SCERET = "TAds8tPP37RbmgR5GZomfx7SsZAn9qtFG33kbAm2Rrm1UYtojM";
	protected static final String ACCESS_TOK = "229995672-hA5zgrJKXR1JIO4OqXOP2OoEtpoFwzPk7pbwecvv";
	protected static final String ACCESS_TOK_SECRET = "VZfpHY4g0ue6sfBRQ0dKT0fyFd4APnNyfbJycH5wCx2SJ";
	protected AccessToken accessToken;
	protected Twitter twitter;
	protected TwitterFactory factory;

	public ConfigClass() {

	}

	public ConfigurationBuilder loadConfig() {
		config = new ConfigurationBuilder();
		config.setDebugEnabled(true).setOAuthConsumerKey(this.CONSUMER_KEYS)
				.setOAuthConsumerSecret(this.CONSUMER_SCERET).setOAuthAccessToken(this.ACCESS_TOK)
				.setOAuthAccessTokenSecret(this.ACCESS_TOK_SECRET);
		return config;
	}

}