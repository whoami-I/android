package com.example.gen_xml;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.gen_xml2.R;

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
		XmlSerializer serializer = Xml.newSerializer();
		try {
			File file = new File(Environment.getExternalStorageDirectory()
					.getPath(), "sms_bak2.xml");
			FileOutputStream fos = new FileOutputStream(file);
			serializer.setOutput(fos, "utf-8");
			// 写xml的头
			serializer.startDocument("utf-8", true);
			// 根节点
			serializer.startTag(null, "smss");
			for (MySms sms : smsLists) {
				// 节点
				serializer.startTag(null, "sms");

				serializer.startTag(null, "address");
				serializer.text(sms.getAddress());
				serializer.endTag(null, "address");

				serializer.startTag(null, "body");
				serializer.text(sms.getBody());
				serializer.endTag(null, "body");

				serializer.startTag(null, "date");
				serializer.text(sms.getDate());
				serializer.endTag(null, "date");

				serializer.endTag(null, "sms");
			}

			serializer.endTag(null, "smss");

			serializer.endDocument();
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
