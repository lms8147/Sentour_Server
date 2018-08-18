package com.tou4u.sentour.content;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HttpRequester {

	private volatile String result;

	public String requestGetData(String url, Map<String, String> header, Map<String, Object> params) {
		result = receiveGetData(url, header, params);
		return result;
	}


	private HttpURLConnection httpConnect(String urlString, boolean read, boolean write, String method,
			Map<String, String> header) {
		URL url;
		try {
			url = new URL(urlString);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setReadTimeout(10000);
			httpURLConnection.setConnectTimeout(10000);
			httpURLConnection.setRequestMethod(method);
			httpURLConnection.setDoInput(read);
			httpURLConnection.setDoOutput(write);

			if (header != null) {
				Iterator<Entry<String, String>> i = header.entrySet().iterator();
				while (true) {
					Entry<String, String> h = i.next();
					httpURLConnection.setRequestProperty(h.getKey(), h.getValue());
					if (!i.hasNext()) {
						break;
					}
				}
			}

			httpURLConnection.connect();
			return httpURLConnection;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String receiveGetData(String url, Map<String, String> header, Map<String, Object> params) {

		String param = null;
		if (params != null) {
			param = "";
			Iterator<Entry<String, Object>> i = params.entrySet().iterator();
			while (true) {
				Entry<String, Object> e = i.next();
				String p = String.format("%s=%s", e.getKey(), e.getValue());
				param += p;
				if (i.hasNext()) {
					param += "&";
				} else {
					break;
				}
			}
		}

		if (param != null) {
			url += ("?" + param);
		}

		HttpURLConnection httpConnection = httpConnect(url, true, false, "GET", header);

		String result = getData(httpConnection, null);
		return result;
	}

	private String getData(HttpURLConnection httpConnection, String param) {

		try {

			if (param != null) {
				OutputStream os = httpConnection.getOutputStream();
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
				bw.write(param);
				bw.flush();
				bw.close();
			}

			int responseStatusCode = httpConnection.getResponseCode();
			InputStream inputStream;
			if (responseStatusCode == HttpURLConnection.HTTP_OK) {
				inputStream = httpConnection.getInputStream();
			} else {
				inputStream = httpConnection.getErrorStream();
			}

			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			StringBuilder sb = new StringBuilder();
			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}

			bufferedReader.close();

			return sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return null;
	}

}
