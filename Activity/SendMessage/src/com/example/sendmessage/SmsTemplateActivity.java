package com.example.sendmessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SmsTemplateActivity extends Activity {
	String object[] = { "I am eating,please call later",
			"I am attending class,please call later",
			"I am driving,please call later", "I am shiting,please call later", };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smstemplate);

		ListView lv_sms_template = (ListView) findViewById(R.id.lv_sms_template);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.sms_template_item, object);
		lv_sms_template.setAdapter(arrayAdapter);

		lv_sms_template.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				intent.setType("text/Contact");
				String smsBody = (String) object[position];
				intent.putExtra("smsBody", smsBody);
				// startActivity(intent);
				// ������Ҫ���ص����ݣ������������Ϊ2
				setResult(2, intent);
				// ҳ�淵�أ����ص�������ǰҳ���ҳ��
				finish();
			}

		});
	}

}