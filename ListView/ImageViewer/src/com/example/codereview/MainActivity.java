package com.example.codereview;

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

import com.example.imageviewer.R;

public class MainActivity extends Activity {

	protected static final int REQUEST_OK = 0;
	protected static final int NOT_FOUND = 1;
	private EditText et_path;
	private Button btn_watch;
	private ImageView iv_image;
	// ��дHandler�����handleMessage������һ���ܵ���Ϣ�ͻ����д˷���
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
		// �������߳�
		new Thread() {
			public void run() {
				// get website
				String path = et_path.getText().toString().trim();
				// new a URL object
				try {
					URL url = new URL(path);
					// �õ�httpURLConnection�������ڻ�ȡ����
					HttpURLConnection connect = (HttpURLConnection) url
							.openConnection();

					// ��������ʱ��5��
					connect.setConnectTimeout(5000);
					// ����get����

					connect.setRequestMethod("GET");

					// ��ȡ���������ص�״̬��

					int code = connect.getResponseCode();
					if (code == 200) {
						// code Ϊ200ʱ��˵��״̬����
						// ��Ϊ�Ƿ���bitmap������Ҫ������Ϣת����BitMap
						InputStream inputStream = connect.getInputStream();
						Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
						// ʹ�����ַ������Լ���Message����Ĵ���
						Message msg = Message.obtain();
						msg.what = REQUEST_OK;
						msg.obj = bitmap;
						handler.sendMessage(msg);
						// tv_code.setText(content);
					} else {
						// ��Դ������,���û�һ����ʾ
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