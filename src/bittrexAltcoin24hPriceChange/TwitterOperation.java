package bittrexAltcoin24hPriceChange;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterOperation {

	Twitter twitter;
	ConfigurationBuilder cb;

	public TwitterOperation() {

		cb = new ConfigurationBuilder();
		// PUT TWITTER API KEYS
		// Give Read&Write access from twitter API
		cb.setDebugEnabled(true).setOAuthConsumerKey("Put here Consumer Key")
				.setOAuthConsumerSecret("Put here Consumer Secret")
				.setOAuthAccessToken("Put here Access Token")
				.setOAuthAccessTokenSecret("Put here AccessToken Secret");

		TwitterFactory tf = new TwitterFactory(cb.build());

		twitter = tf.getInstance();

	}

	public void tweet(String tw) throws TwitterException {
		if (tw == "")
			return;
		Status status = twitter.updateStatus(tw);
		System.out.println("Twitter Updated!");
	}

}
