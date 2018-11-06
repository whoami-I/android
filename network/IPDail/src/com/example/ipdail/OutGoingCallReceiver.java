package com.example.ipdail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

//定义一个广播接收者，这个类继承broadcast，必须实现里面的未实现的方法

public class OutGoingCallReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		System.out.println(getResultData());
		String ipNumber = getResultData();
		ipNumber = "110" + ipNumber;
		setResultData(ipNumber);
	}

}
