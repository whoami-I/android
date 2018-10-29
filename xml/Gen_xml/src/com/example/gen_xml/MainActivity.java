package com.example.gen_xml;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	private ArrayList<MySms> smsLists;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		smsLists = new ArrayList<MySms>();
		for (int i = 0; i < 10; i++) {
			MySms sms = new MySms();
			sms.setAddress("huizhou" + i + "road");
			sms.setBody("I am at " + i);
			sms.setDate("2018/11/" + i);
			smsLists.add(sms);
		}
	}

	public void click(View v) {

		StringBuffer sb = new StringBuffer();
		// 创建xml文件头
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

		// 创建xml根节点
		sb.append("<smss>");
		for (MySms sms : smsLists) {
			sb.append("<sms>");

			sb.append("<address>");
			sb.append(sms.getAddress());
			sb.append("</address>");

			sb.append("<body>");
			sb.append(sms.getBody());
			sb.append("</body>");

			sb.append("<date>");
			sb.append(sms.getDate());
			sb.append("</date>");

			sb.append("</sms>");
		}

		sb.append("</smss>");

		// 将数据保存到sdcard中
		try {
			File file = new File(Environment.getExternalStorageDirectory()
					.getPath(), "smsbak.xml");
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(sb.toString().getBytes());
			fos.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
