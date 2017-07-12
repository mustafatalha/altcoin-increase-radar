package bittrexAltcoin24hPriceChange;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;

public class JSONAnalysis {

	// An Arraylist for coin in the btc market
	private ArrayList<String> marketList;
	// An HashMap for coins' last prices
	private HashMap<String, Double> priceList;
	// An HashMap for coins' price chances on btc depends price like {XXX, 13.2%}
	private HashMap<String, Double> priceChange;

	public JSONAnalysis(JSONArray arr) throws NumberFormatException, JSONException {
		marketList = new ArrayList<>();
		priceList = new HashMap<>();
		priceChange = new HashMap<String, Double>();

		for (int i = 0; i < arr.length(); i++) {
			String marketName = (String) arr.getJSONObject(i).get("MarketName");
			double last = Double.parseDouble(arr.getJSONObject(i).get("Last").toString());
			double prevDay = Double.parseDouble(arr.getJSONObject(i).get("PrevDay").toString());
			double change = 100 * (last - prevDay) / prevDay;

			if (marketName.substring(0, 3).equalsIgnoreCase("BTC")) {
				marketList.add(marketName.substring(4));
				priceList.put(marketName.substring(4), last);
				priceChange.put(marketName.substring(4), change);
			}
		}
	}

	public ArrayList<String> getMarketList() {
		return marketList;
	}

	public void setMarketList(ArrayList<String> marketList) {
		this.marketList = marketList;
	}

	public HashMap<String, Double> getPriceList() {
		return priceList;
	}

	public void setPriceList(HashMap<String, Double> priceList) {
		this.priceList = priceList;
	}

	public HashMap<String, Double> getPriceChange() {
		return priceChange;
	}

	public void setPriceChange(HashMap<String, Double> priceChange) {
		this.priceChange = priceChange;
	}

}
