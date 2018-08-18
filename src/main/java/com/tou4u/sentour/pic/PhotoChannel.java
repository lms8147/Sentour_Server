package com.tou4u.sentour.pic;

public class PhotoChannel {
	
	private String directory;
	private PhotoReader reader;
	private PhotoWriter writer;
	
	public PhotoChannel(String directory) {
		this.directory = directory;
		reader = new PhotoReader(directory);
		writer = new PhotoWriter(directory);
	}
	
	public String getDirectory() {
		return directory;
	}
	
	private String getCurrentTimeStamp() {
		return String.valueOf(System.currentTimeMillis());
	}
	
	public String upload(String data) {
		String fileName = getCurrentTimeStamp();
		writer.savePhoto(fileName, data);
		return fileName;
	}
	
	public String download(String filename) {
		return reader.loadPhoto(filename);
	}
	
	public String[] download(String[] filenames) {
		assert filenames.length < 10 : "";
		String[] photos = new String[filenames.length];
		for(int index = 0; index < filenames.length; index++) {
			photos[index] = reader.loadPhoto(filenames[index]);
		}
		return photos;
	}

}
