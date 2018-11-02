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
		// 点击跳转到activity_test,这是隐式跳转
		Intent intent = new Intent();
		intent.setAction("com.example.testactivity");
		intent.addCategory("android.intent.category.DEFAULT");
		startActivity(intent);
	}

	public void click_call1(View v) {
		// 显示跳转
		// 第二种方式，直接在intent初始化时进行activity的设置
		Intent intent = new Intent(this, TestActivity.class);
		// 第一种显示跳转方式，通过设置当前应用的包名和所需跳转的类名全称
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
