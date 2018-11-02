package com.example.sendmessage;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ContactActivity extends Activity {

	private ListView lv_contact;
	private ArrayList<Contact> contactsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		lv_contact = (ListView) findViewById(R.id.lv_contact);

		contactsList = new ArrayList<Contact>();
		Contact contact = new Contact("mazi", "13548905834");
		contactsList.add(contact);
		contact = new Contact("wanger", "1352222222");
		contactsList.add(contact);
		contact = new Contact("zhangsan", "135333333333");
		contactsList.add(contact);
		contact = new Contact("lisi", "135444444444");
		contactsList.add(contact);
		lv_contact.setAdapter(new ContactAdapter());
		lv_contact.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				intent.setType("text/Contact");
				Contact contact = contactsList.get(position);
				intent.putExtra("name", contact.getName());
				intent.putExtra("phone", contact.getPhone());
				// startActivity(intent);
				// 设置需要返回的数据,将结果码设置为1
				setResult(1, intent);
				// 页面返回，返回到启动当前页面的页面
				finish();
			}

		});
	}

	private class ContactAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return contactsList.size();
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
			View view;
			if (convertView == null) {
				view = View.inflate(getApplicationContext(),
						R.layout.contact_item, null);
			} else {
				view = convertView;
			}
			TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
			TextView tv_number = (TextView) view.findViewById(R.id.tv_number);
			tv_name.setText(contactsList.get(position).getName());
			tv_number.setText(contactsList.get(position).getPhone());
			return view;
		}

	}
}
