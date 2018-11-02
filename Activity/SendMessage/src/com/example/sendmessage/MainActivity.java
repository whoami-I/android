package com.example.sendmessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText et_number;
	private EditText et_smsBody;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et_number = (EditText) findViewById(R.id.et_number);
		et_smsBody = (EditText) findViewById(R.id.et_smsBody);
	}

	public void click_add(View v) {
		Intent intent = new Intent(this, ContactActivity.class);
		// startActivity(intent);
		// 当需要另一个页面的数据时，必须使用这个函数调用
		startActivityForResult(intent, 1);
	}

	public void clickInsertTemplate(View v) {
		Intent intent = new Intent(this, SmsTemplateActivity.class);
		// startActivity(intent);
		startActivityForResult(intent, 1);
	}

	public void click_send(View v) {
		String number = et_number.getText().toString().trim();
		String smsBody = et_smsBody.getText().toString().trim();
		if (TextUtils.isEmpty(number)) {
			Toast.makeText(getApplicationContext(), "please input number",
					Toast.LENGTH_LONG).show();
			return;
		} else if (TextUtils.isEmpty(smsBody)) {
			Toast.makeText(getApplicationContext(), "please input message",
					Toast.LENGTH_LONG).show();
			return;
		}
		System.out.println(100000011);
		android.telephony.SmsManager smsManager = android.telephony.SmsManager
				.getDefault();
		smsManager.sendTextMessage(number, "316540942000", smsBody, null, null);
	}

	// 此函数在请求界面返回时调用
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null)
			return;
		// 使用结果码来判断是哪个窗口返回的
		switch (resultCode) {
		case 1:
			String phone = data.getStringExtra("phone");
			et_number.setText(phone);
			break;
		case 2:
			String smsBody = data.getStringExtra("smsBody");
			et_smsBody.setText(smsBody);
			break;
		default:
			break;
		}

		// super.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
