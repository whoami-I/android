package com.example.ipdail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

//����һ���㲥�����ߣ������̳�broadcast������ʵ�������δʵ�ֵķ���

public class OutGoingCallReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		System.out.println(getResultData());
		String ipNumber = getResultData();
		ipNumber = "110" + ipNumber;
		setResultData(ipNumber);
	}

}
