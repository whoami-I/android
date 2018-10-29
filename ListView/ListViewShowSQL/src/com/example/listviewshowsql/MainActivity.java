package com.example.listviewshowsql;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*这个程序实现了sqlite数据库的增删改查，并且每一次查询都会使用ListView显示数据库里面的内容*/
public class MainActivity extends Activity {

	private MySQLOpenHelper mySQLOpenHelper;
	private ArrayList<Information> informations;
	private ListView lv_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv_info = (ListView) findViewById(R.id.lv_info);

		mySQLOpenHelper = new MySQLOpenHelper(getApplicationContext());
	}

	public void click_add(View v) {
		SQLiteDatabase db = mySQLOpenHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		long insert;
		// 增加条目
		contentValues.put("_id", 1);
		contentValues.put("name", "Bob");
		contentValues.put("phone", "1388888");
		insert = db.insert("info", null, contentValues);
		contentValues.put("_id", 2);
		contentValues.put("name", "Mike");
		contentValues.put("phone", "13111111");
		insert = db.insert("info", null, contentValues);
		contentValues.put("_id", 3);
		contentValues.put("name", "Sarah");
		contentValues.put("phone", "1399999");
		insert = db.insert("info", null, contentValues);
		contentValues.put("_id", 4);
		contentValues.put("name", "John");
		contentValues.put("phone", "13333333");

		insert = db.insert("info", null, contentValues);
		if (insert > 0) {
			Toast.makeText(getApplicationContext(), "insert success",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(getApplicationContext(), "insert fail",
					Toast.LENGTH_LONG).show();
		}
		db.close();
	}

	public void click_delete(View v) {
		SQLiteDatabase db = mySQLOpenHelper.getWritableDatabase();
		int row = db.delete("info", "_id=?", new String[] { "2" });
		Toast.makeText(getApplicationContext(), "delete" + row + "row",
				Toast.LENGTH_LONG).show();
		db.close();
	}

	public void click_update(View v) {
		SQLiteDatabase db = mySQLOpenHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("phone", "110");
		db.update("info", contentValues, "_id=?", new String[] { "1" });
		db.close();
	}

	public void click_query(View v) {
		SQLiteDatabase db = mySQLOpenHelper.getWritableDatabase();
		informations = new ArrayList<Information>();
		Cursor cursor = db.query("info", null, null, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				String id;
				String name;
				String phone;
				// 可以优化成获取列的名字，由名字得到列的index
				id = cursor.getString(cursor.getColumnIndex("_id"));
				name = cursor.getString(cursor.getColumnIndex("name"));
				phone = cursor.getString(cursor.getColumnIndex("phone"));

				System.out.println("id" + id + "   " + "name" + name + "   "
						+ "phone" + phone);

				Information info = new Information();
				info.setId(id);
				info.setName(name);
				info.setPhone(phone);
				informations.add(info);
			}
		}
		db.close();

		lv_info.setAdapter(new MyAdapter());
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// 返回informations的大小
			return informations.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v;
			if (convertView == null) {
				// 获得打气筒
				v = View.inflate(getApplicationContext(), R.layout.info_item,
						null);
			} else {
				v = convertView;
			}
			TextView tv_id = (TextView) v.findViewById(R.id.tv_id);
			TextView tv_name = (TextView) v.findViewById(R.id.tv_name);
			TextView tv_phone = (TextView) v.findViewById(R.id.tv_phone);
			Information info = informations.get(position);
			tv_id.setText(info.getId());
			tv_name.setText(info.getName());
			tv_phone.setText(info.getPhone());
			return v;
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
