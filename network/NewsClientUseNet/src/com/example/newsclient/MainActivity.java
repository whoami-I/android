package com.example.newsclient;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsclientusenet.R;
import com.jit.lib.SmartImageView;

public class MainActivity extends Activity {

	private ArrayList<News> newsList;
	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lv = (ListView) findViewById(R.id.lv);

		initListData();

	}

	private void initListData() {

		new Thread() {

			public void run() {
				try {
					String path = "http://news.qq.com/china_index.shtml";
					URL url = new URL(path);
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();
					connection.setRequestProperty("Accept", "*/*");
					connection.setConnectTimeout(5000);
					int code = connection.getResponseCode();
					if (code == 200) {
						InputStream inputStream = connection.getInputStream();
						// newsList = NewsParser.parse(inputStream);
						String result = StreamTools.readStream(inputStream);

						// 对得到的整个网页源代码进行数据抓取
						newsList = DataParser.parse(result);

						// 更新UI的动作一定不要放在非UI线程中
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								lv.setAdapter(new NewsAdapter());
							}
						});

					}
				} catch (Exception e) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(getApplicationContext(), "网络繁忙",
									Toast.LENGTH_LONG).show();
						}
					});

					e.printStackTrace();
				}
			}

		}.start();

	}

	private class NewsAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return newsList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		private void setView(View v, int position) {
			TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
			TextView tv_description = (TextView) v
					.findViewById(R.id.tv_description);
			SmartImageView iv = (SmartImageView) v.findViewById(R.id.iv);
			News news = newsList.get(position);
			tv_title.setText(news.getTitle());
			tv_description.setText(news.getContent());
			iv.setImageUrl(news.getImagePath());

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v;
			if (convertView == null) {
				v = View.inflate(getApplicationContext(), R.layout.news_item,
						null);
			} else {
				v = convertView;
			}
			setView(v, position);
			return v;
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
