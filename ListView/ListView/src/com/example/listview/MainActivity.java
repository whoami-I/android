package com.example.listview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lv = (ListView) findViewById(R.id.lv);
		lv.setAdapter(new MyAdapter());
	}

	private class MyAdapter extends BaseAdapter {

		// 返回ListView中内容的个数
		@Override
		public int getCount() {
			return 10;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		// 生成ListView中的内容，返回对象
		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// System.out.println("I am " + arg0);
			TextView tv = null;
			if (arg1 == null) {
				tv = new TextView(getApplicationContext());
				System.out.println("create new TextView" + arg0);
			} else {
				tv = (TextView) arg1;
				System.out.println("use old TextView" + arg0);
			}
			tv.setText("I am " + arg0);
			tv.setTextColor(Color.BLACK);

			return tv;
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
