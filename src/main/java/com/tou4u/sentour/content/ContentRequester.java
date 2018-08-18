package com.tou4u.sentour.content;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ContentRequester {

	private ContentAPI contentAPI;

	public ContentRequester() {
		contentAPI = new ContentAPI();
	}

	public String response(String contentType, String radius, String lat, String lng, String sky, String hashTag) {
		/*
		 * String lat = "37.568477"; String lng = "126.981611"; String sky = ""; String
		 * hashTag = "";
		 */

		List<Content.Builder> contentBuilders = requestContents(contentType, radius, lat, lng);
		List<Content> contents = calcUnlike(contentBuilders, sky, hashTag);

		String result = toJSONString(contents);

		return result;
	}

	private List<Content.Builder> requestContents(String contentType, String radius, String lat, String lng) {
		List<Content.Builder> contentBuilders;
		contentBuilders = contentAPI.getContents(new GeoPosition(lat, lng), radius, contentType);
		return contentBuilders;
	}

	private List<Content> calcUnlike(List<Content.Builder> contentBuilders, String sky, String hashTag) {
		List<Content> contents = new ArrayList<>();
		
		StaticsRequester staticsRequester = new StaticsRequester();
		for(Content.Builder b : contentBuilders) {
			double[] statics = staticsRequester.statics(b.toString(), sky, hashTag);
			b.setUnlike((float) statics[0]);
			b.setUnlikeSky((float) statics[1]);
			contents.add(b.build());
		}

		return contents;
	}

	private String toJSONString(List<Content> contents) {
		JSONObject root = new JSONObject();
		JSONObject response = new JSONObject();
		JSONObject body = new JSONObject();

		JSONArray items = new JSONArray();

		Iterator<Content> i = contents.iterator();

		while (i.hasNext()) {
			JSONObject item = buildData(i.next());
			items.add(item);
		}

		body.put("items", items);
		response.put("body", body);
		root.put("response", response);

		return root.toJSONString();
	}

	private JSONObject buildData(Content content) {
		JSONObject data = new JSONObject();
		data.put("contentId", content.getContentID());
		data.put("dist", String.valueOf(content.getDist()));
		data.put("title", content.getTitle());
		if(content.getFirstImageUrl() != null) {
			data.put("imageURL", content.getFirstImageUrl());
		}
		data.put("unlike", String.format("%.5f",content.getUnlike()));
		data.put("unlikeSky", String.format("%.5f",content.getUnlikeSky()));
		data.put("lat", content.getGeoPosition().getLat());
		data.put("lng", content.getGeoPosition().getLon());
		return data;
	}
}
