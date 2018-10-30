package com.example.diff_cache_getfile;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void click_cache(View v) {
		// 创建缓存和创建文件不一样
		try {
			File file = new File(getCacheDir(), "info.txt");
			FileOutputStream fos = new FileOutputStream(file);
			fos.write("haha".getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void click_file(View v) {
		try {
			FileOutputStream fos = openFileOutput("info.txt", 0);
			fos.write("hah".getBytes());
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
