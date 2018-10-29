package com.example.login;

import java.io.File;
import java.io.FileOutputStream;

public class UserInfo {

	public static boolean save(String username, String password) {
		String result = username + "##" + password;
		try {
			File file = new File("/data/data/com.example.login/info.txt");
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(result.getBytes());
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
