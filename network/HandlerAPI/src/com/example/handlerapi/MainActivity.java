package com.example.handlerapi;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tv_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv_text = (TextView) findViewById(R.id.tv_text);
		// ����������Ը���UI
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				System.out.println("I am out");
				// ����UI
				tv_text.setText("haha");
			}
		}, 5000);

		// Ҳ����ʹ�ö�ʱ��ʵ���ӳ٣����Ƕ�ʱ�����治��ֱ��ִ�и���UI�Ĺ���������ʹ��runOnUIThread���API
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				// ���Բ鿴���API��˵��
				runOnUiThread(new Runnable() {
					public void run() {
						tv_text.setText("timer");
					}
				});
				System.out.println("timer task executed!");
			}

		};
		timer.schedule(timerTask, 3000);
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
