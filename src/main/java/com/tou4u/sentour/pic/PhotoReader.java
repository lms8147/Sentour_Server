package com.tou4u.sentour.pic;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

public class PhotoReader {
	private String directory;

	public PhotoReader(String directory) {
		this.directory = directory;
	}

	private String encodeBytes(byte[] data) {
		String encodedData = Base64.getUrlEncoder().encodeToString(data);
		return encodedData;
	}

	public String loadPhoto(String fileName) {
		byte[] data = readBytes(directory + fileName);
		return encodeBytes(data);
	}

	private byte[] readBytes(String path) {
		try {
			ByteArrayOutputStream bao = new ByteArrayOutputStream();

			FileInputStream fs = new FileInputStream(path);
			BufferedInputStream bi = new BufferedInputStream(fs);

			int bytesRead = 0;
			byte[] buf = new byte[1024];
			while ((bytesRead = bi.read(buf)) > 0) {
				bao.write(buf, 0, bytesRead);
			}
			bi.close();
			bao.close();
			return bao.toByteArray();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
