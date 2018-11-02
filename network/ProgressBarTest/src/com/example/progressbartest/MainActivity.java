package com.example.progressbartest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	private ProgressBar pg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/*
		 * pg = (ProgressBar) findViewById(R.id.pg);
		 * 
		 * pg.setMax(100); pg.setProgress(50);
		 */

		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
		ProgressBar pg = (ProgressBar) View.inflate(getApplicationContext(),
				R.layout.progress, null);

		pg.setMax(100);
		pg.setProgress(50);
		ll.addView(pg);

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
