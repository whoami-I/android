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
		// 在这里面可以更新UI
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				System.out.println("I am out");
				// 更新UI
				tv_text.setText("haha");
			}
		}, 5000);

		// 也可以使用定时器实现延迟，但是定时器里面不能直接执行更新UI的工作，可以使用runOnUIThread这个API
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				// 可以查看这个API的说明
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
