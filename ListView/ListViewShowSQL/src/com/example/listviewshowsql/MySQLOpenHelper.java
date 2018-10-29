package com.example.listviewshowsql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLOpenHelper extends SQLiteOpenHelper {

	MySQLOpenHelper(Context context) {
		super(context, "mydatabase", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// �������ݿ⣬������ݿ���һ����info��info�����У�id��name��phone
		db.execSQL("create table info(_id integer, name varchar(20),phone varchar(20))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
