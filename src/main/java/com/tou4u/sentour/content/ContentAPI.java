package com.tou4u.sentour.content;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.tou4u.sentour.AppConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ContentAPI {

	private String serviceKey = AppConfig.VISITKOREA_API_KEY;

	private HashMap<String, String> header;

	private HttpRequester requester;

	public ContentAPI() {
		requester = new HttpRequester();
		header = new HashMap<>();
		header.put("Content-Type", "application/json");
	}

	public List<Content.Builder> getContents(GeoPosition geoPosition, String radius, String contentType) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("MobileOS", "AND");
		params.put("MobileApp", "SimT");
		params.put("serviceKey", serviceKey);
		params.put("numOfRows", "100");
		params.put("pageNo", "1");
		params.put("listYN", "Y");
		params.put("contentTypeId", contentType);
		params.put("mapY", geoPosition.getLat());
		params.put("mapX", geoPosition.getLon());
		params.put("radius", radius);
		params.put("_type", "json");

		String url = AppConfig.VISITKOREA_API_URL;
		String result = requester.requestGetData(url, header, params);

		LinkedList<Content.Builder> contents = new LinkedList<>();

		JSONParser parser = new JSONParser();
		try {
			JSONObject root = (JSONObject) parser.parse(result);
			JSONObject response = (JSONObject) root.get("response");
			JSONObject body = (JSONObject) response.get("body");
			Long numOfRows = (Long) body.get("numOfRows");
			Long pageNo = (Long) body.get("pageNo");
			Long totalCount = (Long) body.get("totalCount");
			JSONArray items = (JSONArray) ((JSONObject) body.get("items")).get("item");

			for(int i = 0; i < items.size(); i++) {
				JSONObject content = (JSONObject) items.get(i);
				String contentID = content.get("contentid").toString();
				String dist = content.get("dist").toString();
				String iamgeURL = null;
				if (content.containsKey("firstimage")) {
					iamgeURL = content.get("firstimage").toString();
				}
				String title = content.get("title").toString();
				String mapX = content.get("mapx").toString();
				String mapY = content.get("mapy").toString();

				Content.Builder builder = new Content.Builder();
				builder.setContentID(contentID);
				builder.setDist(Integer.parseInt(dist));
				builder.setFirstImageUrl(iamgeURL);
				builder.setGeoPosition(new GeoPosition(mapY, mapX));
				builder.setTitle(title);
				contents.add(builder);
			}

			return contents;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
