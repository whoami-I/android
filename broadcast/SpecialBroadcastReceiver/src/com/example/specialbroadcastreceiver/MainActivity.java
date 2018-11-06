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
		// ����Ĺ㲥�����ߣ�����Ҫ���嵥�ļ���������������Ҫʹ�ô��붯̬��
		// ע����������Ĺ㲥������
		// ����㲥�����߰���������������
		// ����ע���ԭ���ǣ���ǰ���嵥�ļ�����ע��Ĺ㲥�������Ǿ�̬��
		// ֻҪһ���ֹ㲥�ͻ���գ����ǵ�ע����ֹ㲥������ܶ�Ļ����ͻ����˲��
		// ��Ҫ�������ڴ�ռ䣬������ֶ�̬��ע�᷽�����ǣ���Ҫʱ��ע�ᣬ��ʱ
		// �㲥���Խ��գ�������Ҫʱ����ע����
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.SCREEN_ON");
		filter.addAction("android.intent.action.SCREEN_OFF");
		registerReceiver(screenReceiver, filter);
	}

	// �ڽ����˳�ʱ���ͻ�ִ�����������ע��������㲥������
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
