package bittrexAltcoin24hPriceChange;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import twitter4j.TwitterException;

public class Driver {

	private static final Logger logger = Logger.getLogger("MyLog");
	static DecimalFormat dfChange = new DecimalFormat("####0.00");
	static DecimalFormat dfPrice = new DecimalFormat("####.########");

	public static void main(String[] args) throws JSONException, TwitterException, InterruptedException {
		FileHandler fh;
		try {
			// This block configure the logger with handler and formatter
			fh = new FileHandler("App.log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		final String url = "https://bittrex.com/api/v1.1/public/getmarketsummaries";
		String tweet = "";
		JSONObject jsonOb;
		JSONArray resultArr;
		JSONAnalysis lists;
		JSONParser jsonP = new JSONParser();
		TwitterOperation to = new TwitterOperation();

		while (true) {
			jsonOb = jsonP.getJSONFromUrl(url);
			resultArr = jsonOb.getJSONArray("result");
			lists = new JSONAnalysis(resultArr);
			for (int i = 0; i < lists.getMarketList().size(); i++) {
				if (lists.getPriceChange().get(lists.getMarketList().get(i)) > 10.0) {
					tweet = giveMeTweetPost(jsonOb, resultArr, lists,i);
					// Lets tweet!
					to.tweet(tweet);
					// Log tweet info to App.log in project folder
					logger.info("Twitter Updated: \n" + tweet);
				}
			}
			// This statement handles the period of update
			TimeUnit.MINUTES.sleep(60);
		}
	}
	
	public static String giveMeTweetPost(JSONObject jsonOb, JSONArray resultArr, JSONAnalysis lists, int i){
		return "Something is happening!\n\n$" + lists.getMarketList().get(i) + " price is "
				+ dfPrice.format(Double
						.parseDouble(lists.getPriceList().get(lists.getMarketList().get(i)).toString()))
				+ " btc" + " (24h Change: +"
				+ dfChange.format(lists.getPriceChange().get(lists.getMarketList().get(i))) + "%)\n\n"
				+ "#bitcoin #btc" + " $btc $eth #ltc $etc $sc $xvg $zec $xrp $strat";
	}
}
