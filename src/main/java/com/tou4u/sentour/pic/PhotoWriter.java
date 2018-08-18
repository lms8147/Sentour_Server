package com.tou4u.sentour.pic;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Base64;

public class PhotoWriter {
	private String directory;

	public PhotoWriter(String directory) {
		this.directory = directory;
	}

	private byte[] decodedBytes(String encodedData) {

		byte[] data = Base64.getUrlDecoder().decode(encodedData);
		return data;
	}

	public String savePhoto(String fileName, String encodedData) {

		byte[] data = decodedBytes(encodedData);
		ByteArrayInputStream bai = new ByteArrayInputStream(data);
		try {
			String[] mimeType = URLConnection.guessContentTypeFromStream(bai).split("/");
			if (mimeType[0].equals("image")) {
				String fileExtension = "";
				if (mimeType[1].equals("jpeg")) {
					fileExtension = ".jpg";
				}
				String path = directory + fileName + fileExtension;
				writeBytes(path, data);
				return fileName + fileExtension;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void writeBytes(String path, byte[] data) {

		try {
			FileOutputStream fo = new FileOutputStream(path);
			BufferedOutputStream bo = new BufferedOutputStream(fo);

			bo.write(data);

			bo.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
