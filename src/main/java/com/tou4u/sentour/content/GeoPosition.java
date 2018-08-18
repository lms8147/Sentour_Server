package com.tou4u.sentour.content;

public class GeoPosition {

	private static final float BASE = 10000;

	private int lat;
	private int lon;

	public GeoPosition(double lat, double lon) {
		this.lat = (int) (lat * BASE);
		this.lon = (int) (lon * BASE);
	}

	public GeoPosition(String lat, String lon) {

		this.lat = (int) (Float.parseFloat(lat) * BASE);
		this.lon = (int) (Float.parseFloat(lon) * BASE);
	}

	public String getLat() {
		float lat = this.lat / BASE;
		String format = String.format("%.5f", lat);
		return format;
	}

	public String getLon() {
		float lon = this.lon / BASE;
		String format = String.format("%.5f", lon);
		return format;
	}

}
