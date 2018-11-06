package com.example.orderedbroadcastreceive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class FirstReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String content = intent.getStringExtra("content");
		System.out.println("First receiver:" + content);
	}

}
