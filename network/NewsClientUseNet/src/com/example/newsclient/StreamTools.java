package com.example.newsclient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTools {
	// °Ñstream×ª³É×Ö·û´®
	public static String readStream(InputStream in) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = -1;
		byte buffer[] = new byte[1024];
		while ((len = in.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
		in.close();
		String content = new String(baos.toByteArray(), "gbk");
		return content;
	}
}
