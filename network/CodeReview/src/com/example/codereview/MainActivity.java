package com.example.codereview;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private EditText et_path;
	private Button btn_watch;
	private TextView tv_code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// ��δ�����Ϊ��ʹ�ÿ��������߳��з�������
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

		System.out.println("output123");
		// get website
		String path = et_path.getText().toString().trim();
		// new a URL object
		try {
			System.out.println(path);
			URL url = new URL(path);
			// �õ�httpURLConnection�������ڻ�ȡ����
			HttpURLConnection connect = (HttpURLConnection) url
					.openConnection();

			// ��������ʱ��5��
			connect.setConnectTimeout(5000);
			// ����get����

			connect.setRequestMethod("GET");
			System.out.println("output123");

			// ��ȡ���������ص�״̬��

			int code = connect.getResponseCode();
			System.out.println(code);
			if (code == 200) {
				// code Ϊ200ʱ��˵��״̬����
				InputStream inputStream = connect.getInputStream();
				// ����ת�����ַ������������������չʾ��textview����
				String content = StreamTools.readStream(inputStream);
				tv_code.setText(content);
				System.out.println("output");
			}
		} catch (Exception e) {
			System.out.println("error");
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