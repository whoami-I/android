package com.example.activityproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		TextView tv_name = (TextView) findViewById(R.id.tv_name);
		TextView tv_gender = (TextView) findViewById(R.id.tv_gender);
		TextView tv_description = (TextView) findViewById(R.id.tv_description);

		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		int gender = intent.getIntExtra("gender", -1);
		System.out.println("result:" + gender);
		switch (gender) {
		case R.id.rb_male:
			tv_gender.setText("male");
			break;
		case R.id.rb_female:
			tv_gender.setText("female");
			break;
		case R.id.rb_other:
			tv_gender.setText("ÈËÑý");
			break;

		default:
			tv_gender.setText("unknown gender");
			break;
		}

		tv_name.setText(name);
		tv_description.setText("good man");

	}

}
