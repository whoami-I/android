package com.example.specialbroadcastreceiver;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	private ScreenReceiver screenReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		screenReceiver = new ScreenReceiver();
		// 特殊的广播接收者，不需要在清单文件里面声明，而是要使用代码动态的
		// 注册这种特殊的广播接收者
		// 特殊广播接受者包括：锁屏、解锁
		// 这种注册的原因是，以前在清单文件里面注册的广播接收者是静态的
		// 只要一发现广播就会接收，但是当注册此种广播的软件很多的话，就会出现瞬间
		// 需要大量的内存空间，因此这种动态的注册方法就是，需要时就注册，这时
		// 广播可以接收，当不需要时，就注销掉
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.SCREEN_ON");
		filter.addAction("android.intent.action.SCREEN_OFF");
		registerReceiver(screenReceiver, filter);
	}

	// 在界面退出时，就会执行这个方法，注销掉这个广播接收者
	@Override
	protected void onDestroy() {
		unregisterReceiver(screenReceiver);
		super.onDestroy();
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
