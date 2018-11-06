package com.example.disorderedbroadcastreceive;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DisorderedBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		System.out.println("receive myaction");
		String name = arg1.getStringExtra("name");
		System.out.println("name:" + name);
	}

}
