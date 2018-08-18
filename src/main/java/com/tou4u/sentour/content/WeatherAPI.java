package com.tou4u.sentour.content;

import java.util.HashMap;

import com.tou4u.sentour.AppConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WeatherAPI {

	private String appKey = AppConfig.SK_DEV_API_KEY;

	private HashMap<String, String> header;

	private HttpRequester requester;

	public WeatherAPI() {
		requester = new HttpRequester();
		header = new HashMap<>();
		header.put("appKey", appKey);
		header.put("Content-Type", "application/json");
	}

	public WeatherData getSkyData(GeoPosition geoPosition) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("version", 1);
		params.put("lat", geoPosition.getLat());
		params.put("lon", geoPosition.getLon());

		String url = AppConfig.SK_DEV_API_URL;
		String result = requester.requestGetData(url, header, params);

		JSONParser parser = new JSONParser();
		try {
			JSONObject root = (JSONObject) parser.parse(result);
			JSONObject weather = (JSONObject) root.get("weather");
			JSONObject hourly = (JSONObject) ((JSONArray) weather.get("hourly")).get(0);
			JSONObject sky = (JSONObject) hourly.get("sky");
			String name = (String) sky.get("name");
			String code = (String) sky.get("code");
			JSONObject temperature = (JSONObject) hourly.get("temperature");
			String tc = (String) temperature.get("tc");

			WeatherData weatherData = new WeatherData(name, code, tc);
			return weatherData;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
