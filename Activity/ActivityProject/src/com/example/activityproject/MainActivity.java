package com.example.activityproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText et_name;
	private RadioGroup rg_gender;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		et_name = (EditText) findViewById(R.id.et_name);
		rg_gender = (RadioGroup) findViewById(R.id.rg_gender);
	}

	public void click_calculate(View v) {
		String name = et_name.getText().toString().trim();
		if (TextUtils.isEmpty(name)) {
			Toast.makeText(getApplicationContext(), "please input name",
					Toast.LENGTH_LONG).show();
			return;
		}
		int genderIndex = rg_gender.getCheckedRadioButtonId();
		if (genderIndex == -1) {
			Toast.makeText(getApplicationContext(), "please input gender",
					Toast.LENGTH_LONG).show();
			return;
		}
		Intent intent = new Intent(this, ResultActivity.class);
		// 传递数据到ResultActivity这个界面

		intent.putExtra("name", name);
		intent.putExtra("gender", genderIndex);
		System.out.println("main:" + genderIndex);
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
