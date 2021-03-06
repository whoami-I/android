package com.example.codereview;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	protected static final int REQUEST_OK = 0;
	protected static final int NOT_FOUND = 1;
	private EditText et_path;
	private Button btn_watch;
	private TextView tv_code;
	// 重写Handler里面的handleMessage方法，一接受到消息就会运行此方法
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			String content = (String) msg.obj;
			switch (what) {
			case REQUEST_OK:
				tv_code.setText(content);
				break;
			case NOT_FOUND:
				Toast.makeText(getApplicationContext(), "not found",
						Toast.LENGTH_LONG).show();
				break;
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 这段代码是为了使得可以在主线程中访问网络
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		et_path = (EditText) findViewById(R.id.et_path);
		btn_watch = (Button) findViewById(R.id.btn_watch);
		tv_code = (TextView) findViewById(R.id.tv_code);
	}

	public void click(View v) {
		// 创建子线程
		new Thread() {
			public void run() {
				// get website
				String path = et_path.getText().toString().trim();
				// new a URL object
				try {
					URL url = new URL(path);
					// 拿到httpURLConnection对象，用于获取数据
					HttpURLConnection connect = (HttpURLConnection) url
							.openConnection();

					// 设置请求超时，5秒
					connect.setConnectTimeout(5000);
					// 发送get请求

					connect.setRequestMethod("GET");

					// 获取服务器返回的状态码

					int code = connect.getResponseCode();
					if (code == 200) {
						// code 为200时，说明状态正常
						InputStream inputStream = connect.getInputStream();
						// 把流转换成字符串，把流里面的数据展示在textview里面
						String content = StreamTools.readStream(inputStream);
						// 由于不能在子线程中更新UI的内容，因此使用handler机制，发送一个消息给主线程
						// 让主线程更新UI，只要一发消息，handler里面的handleMessage方法就会运行
						Message msg = new Message();
						msg.what = REQUEST_OK;
						msg.obj = content;
						handler.sendMessage(msg);
						// tv_code.setText(content);
					} else {
						// 资源不存在,给用户一个提示
						Message msg = new Message();
						msg.what = NOT_FOUND;
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			};

		}.start();

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
