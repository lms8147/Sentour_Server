package com.tou4u.sentour.content;

public class Content {

	public static final int CONTENT_TYPE_SPOT = 12;
	public static final int CONTENT_TYPE_CULTURE = 14;
	public static final int CONTENT_TYPE_FESTIVAL = 15;
	public static final int CONTENT_TYPE_COURSE = 25;
	public static final int CONTENT_TYPE_LEPORTS = 28;
	public static final int CONTENT_TYPE_SHOPPING = 38;
	public static final int CONTENT_TYPE_RESTAURANT = 39;

	private String contentID;
	private int dist;
	private String title;
	private String contentTypeID;
	private String firstImageUrl;
	private String overview;

	private GeoPosition geoPosition;
	
	private float unlike = 0;
	private float unlike_sky = 0;
	
	public float getUnlike() {
        return unlike;
    }
	
	public float getUnlikeSky() {
        return unlike_sky;
    }

	public String getContentID() {
		return contentID;
	}

	public int getDist() {
		return dist;
	}

	public String getTitle() {
		return title;
	}

	public String getContentTypeID() {
		return contentTypeID;
	}

	public String getFirstImageUrl() {
		return firstImageUrl;
	}

	public String getOverview() {
		return overview;
	}

	public GeoPosition getGeoPosition() {
		return geoPosition;
	}

	public static final class Builder {
		private Content info;

		public Builder() {
			info = new Content();
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return info.contentID;
		}

		public Content build() {
			return info;
		}
		
		public void setUnlike(float unlike) {
            info.unlike = unlike;
        }
		
		public void setUnlikeSky(float unlike) {
            info.unlike_sky = unlike;
        }

		public void setContentID(String contentID) {
			info.contentID = contentID;
		}

		public void setDist(int dist) {
			info.dist = dist;
		}

		public void setTitle(String title) {
			info.title = title;
		}

		public void setContentTypeID(String contentTypeID) {
			info.contentTypeID = contentTypeID;
		}

		public void setFirstImageUrl(String firstImageUrl) {
			info.firstImageUrl = firstImageUrl;
		}

		public void setGeoPosition(GeoPosition geoPosition) {
			info.geoPosition = geoPosition;
		}

		public void setOverview(String overview) {
			info.overview = overview;
		}
	}
}
