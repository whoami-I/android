package com.example.login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText et_username;
	private EditText et_password;
	private CheckBox cb_remember;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		cb_remember = (CheckBox) findViewById(R.id.cb_remember);
		Button btn_login = (Button) findViewById(R.id.btn_login);
		Map<String, String> map = readInfo();
		if (map != null) {
			String name = map.get("name");
			String password = map.get("password");
			et_username.setText(name);
			et_password.setText(password);
		}
		btn_login.setOnClickListener(new MyListener());
	}

	private class MyListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			String username = et_username.getText().toString().trim();
			String password = et_password.getText().toString().trim();
			if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
				Toast.makeText(MainActivity.this, "用户名或密码不能为空",
						Toast.LENGTH_LONG).show();
			} else {
				if (cb_remember.isChecked()) {
					boolean isSave = UserInfo.save(username, password);
					if (isSave) {
						Toast.makeText(MainActivity.this, "保存成功",
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(MainActivity.this, "保存不成功",
								Toast.LENGTH_LONG).show();
					}
				}

			}

		}

	}

	public Map<String, String> readInfo() {
		Map<String, String> maps = new HashMap<String, String>();
		try {
			File file = new File("/data/data/com.example.login/info.txt");
			FileInputStream fis = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis));

			String content = reader.readLine();
			String[] splits = content.split("##");
			String name = splits[0];
			String passwd = splits[1];
			maps.put("name", name);
			maps.put("password", passwd);
			reader.close();
			fis.close();
			return maps;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
