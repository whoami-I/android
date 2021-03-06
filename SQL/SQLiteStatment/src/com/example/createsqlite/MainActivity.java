package com.example.createsqlite;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.sqlitestatement.R;

public class MainActivity extends Activity {

	private MyOpenHelper myOpenHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myOpenHelper = new MyOpenHelper(getApplicationContext());

	}

	public void clickAdd(View v) {
		SQLiteDatabase writableDatabase = myOpenHelper.getWritableDatabase();
		writableDatabase.execSQL("insert into info(_id,name) values(?,?)",
				new Object[] { "1003", "john" });
		writableDatabase.execSQL("insert into info(_id,name) values(?,?)",
				new Object[] { "1004", "kim" });
		writableDatabase.execSQL("insert into info(_id,name) values(?,?)",
				new Object[] { "1005", "Album" });
		writableDatabase.execSQL("insert into info(_id,name) values(?,?)",
				new Object[] { "1006", "Obama" });
		writableDatabase.close();
	}

	public void clickDelete(View v) {
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		db.execSQL("delete from info where name=?", new Object[] { "kim" });
		db.close();
	}

	public void clickUpdate(View v) {
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		db.execSQL("update info set name=? where _id=1003",
				new Object[] { "kim" });
		db.close();
	}

	public void clickFind(View v) {
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select _id,name from info", null);
		// System.out.println("111111");
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				// 获取第0列的内容，也就是_id的内容
				String id = cursor.getString(0);
				// System.out.println("22222");
				// System.out.println(id);
				String name = cursor.getString(1);
				System.out.println(id + "    " + name);
			}
		}
		db.close();
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
