package com.example.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void click1(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("hint");
		builder.setMessage("click button1");
		builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("ok");
			}
		});

		builder.setNegativeButton("cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.out.println("cancel");
					}
				});
		// 一定要有这一步，否则显示不出来
		builder.show();

	}

	public void click2(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("select the course");
		final String items[] = { "C", "C++", "Android", "Java", "C#" };
		builder.setSingleChoiceItems(items, -1,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getApplicationContext(),
								items[which] + " selected", Toast.LENGTH_LONG)
								.show();
						// 消除对话框
						dialog.dismiss();
					}
				});
		// 一定要有这一步，否则显示不出来
		builder.show();

	}

	public void click3(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("select the fruit you like");
		final String items[] = { "watermelon", "pear", "apple", "grape",
				"peach" };
		final boolean[] checkedItems = { false, false, false, false, false, };
		builder.setMultiChoiceItems(items, checkedItems,
				new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {

					}
				});
		builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				for (int i = 0; i < items.length; i++) {
					if (checkedItems[i]) {
						System.out.println(items[i]);
					}
				}

				dialog.dismiss();
			}

		});
		// 一定要有这一步，否则显示不出来
		builder.show();

	}

	public void click4(View v) {
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setTitle("loading");
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.show();
		new Thread() {
			public void run() {
				for (int i = 0; i < 100; i++) {
					dialog.setProgress(i);
					SystemClock.sleep(50);
				}
				dialog.dismiss();

			};

		}.start();
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
