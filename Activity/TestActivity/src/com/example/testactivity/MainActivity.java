package com.example.testactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	public void click_call(View v) {
		// �����ת��activity_test,������ʽ��ת
		Intent intent = new Intent();
		intent.setAction("com.example.testactivity");
		intent.addCategory("android.intent.category.DEFAULT");
		startActivity(intent);
	}

	public void click_call1(View v) {
		// ��ʾ��ת
		// �ڶ��ַ�ʽ��ֱ����intent��ʼ��ʱ����activity������
		Intent intent = new Intent(this, TestActivity.class);
		// ��һ����ʾ��ת��ʽ��ͨ�����õ�ǰӦ�õİ�����������ת������ȫ��
		// intent.setClassName("com.example.testactivity",
		// "com.example.testactivity.TestActivity");
		startActivity(intent);
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
