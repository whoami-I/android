package com.example.getstorage;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TextView tv_usable_size = (TextView) findViewById(R.id.textView1);
		TextView tv_total_size = (TextView) findViewById(R.id.textView2);

		long usableSpace = Environment.getExternalStorageDirectory()
				.getUsableSpace();
		long totalSpace = Environment.getExternalStorageDirectory()
				.getTotalSpace();

		String format_usable_size = android.text.format.Formatter
				.formatFileSize(this, usableSpace);
		String format_total_size = android.text.format.Formatter
				.formatFileSize(this, totalSpace);
		tv_usable_size.setText("可用空间:" + format_usable_size);
		tv_total_size.setText("总空间 :" + format_total_size);
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
