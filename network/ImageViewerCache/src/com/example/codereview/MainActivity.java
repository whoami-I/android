package com.example.codereview;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.imageviewercache.R;

public class MainActivity extends Activity {

	protected static final int REQUEST_OK = 0;
	protected static final int NOT_FOUND = 1;
	private EditText et_path;
	private Button btn_watch;
	private ImageView iv_image;
	// 重写Handler里面的handleMessage方法，一接受到消息就会运行此方法
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case REQUEST_OK:
				iv_image.setImageBitmap((Bitmap) msg.obj);
				break;
			case NOT_FOUND:
				Toast.makeText(getApplicationContext(), "not found",
						Toast.LENGTH_LONG).show();
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		et_path = (EditText) findViewById(R.id.et_path);
		btn_watch = (Button) findViewById(R.id.btn_watch);
		iv_image = (ImageView) findViewById(R.id.iv_picture);
	}

	public void click(View v) {
		// 创建子线程
		new Thread() {
			public void run() {

				try {
					// 使用缓存的方式
					File file = new File(getCacheDir(), "test.jpg");
					if (file.exists() && file.length() > 0) {
						new BitmapFactory();
						Bitmap bitmap = BitmapFactory.decodeFile(file
								.getAbsolutePath());
						Message msg = Message.obtain();
						msg.what = REQUEST_OK;
						msg.obj = bitmap;
						handler.sendMessage(msg);
						System.out.println("use cache");
					} else {
						// get website
						String path = et_path.getText().toString().trim();
						// new a URL object
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
							// 因为是返回bitmap，所以要将流信息转换成BitMap
							InputStream inputStream = connect.getInputStream();

							file = new File(getCacheDir(), "test.jpg");
							FileOutputStream fos = new FileOutputStream(file);
							int len = -1;
							byte[] buffer = new byte[1024];
							while ((len = inputStream.read(buffer)) != -1) {
								fos.write(buffer, 0, len);
							}
							fos.close();
							inputStream.close();
							Bitmap bitmap = BitmapFactory.decodeFile(file
									.getAbsolutePath());
							// 使用这种方法可以减少Message对象的创建
							Message msg = Message.obtain();
							msg.what = REQUEST_OK;
							msg.obj = bitmap;
							handler.sendMessage(msg);
							System.out.println("use internet");
							// tv_code.setText(content);
						} else {
							// 资源不存在,给用户一个提示
							Message msg = new Message();
							msg.what = NOT_FOUND;
							handler.sendMessage(msg);
						}
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
