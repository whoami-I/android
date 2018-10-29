package com.example.login;

import android.app.Activity;
import android.content.SharedPreferences;
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

import com.example.sp.R;

public class MainActivity extends Activity {

	private EditText et_username;
	private EditText et_password;
	private CheckBox cb_save;
	private CheckBox cb_auto_login;
	private Button btn_login;

	private SharedPreferences sp;

	private String username;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		cb_save = (CheckBox) findViewById(R.id.cb_save);
		cb_auto_login = (CheckBox) findViewById(R.id.cb_auto_login);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new MyListener());

		sp = getSharedPreferences("config", MODE_PRIVATE);

		// 读取文件，是否自动登录，是否保存密码和账号
		boolean is_auto_login = sp.getBoolean("is_auto_login", false);
		boolean is_save = sp.getBoolean("is_save", false);
		username = sp.getString("username", "");
		password = sp.getString("password", "");

		// 设置控件的状态
		cb_save.setChecked(is_save);
		cb_auto_login.setChecked(is_auto_login);

		// 填充账号密码
		if (username != null && password != null) {
			et_username.setText(username);
			et_password.setText(password);

		}
		// 如果自动登录，则登录
		if (is_auto_login) {
			login();
		}

	}

	private void saveState() {
		SharedPreferences.Editor editor = sp.edit();
		if (cb_save.isChecked()) {
			editor.putString("username", username);
			editor.putString("password", password);
		}

		editor.putBoolean("is_auto_login", cb_auto_login.isChecked());
		editor.putBoolean("is_save", cb_save.isChecked());
		editor.commit();

	}

	private void login() {
		username = et_username.getText().toString().trim();
		password = et_password.getText().toString().trim();
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			Toast.makeText(MainActivity.this, "用户名或密码不能为空", Toast.LENGTH_LONG)
					.show();
		} else {
			saveState();
		}
	}

	private class MyListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			login();

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
