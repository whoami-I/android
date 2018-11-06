package com.example.smslistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

public class SmsListenerReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Object[] obj = (Object[]) arg1.getExtras().get("pdus");
		for (Object object : obj) {
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) object);
			String messageBody = smsMessage.getMessageBody();
			String address = smsMessage.getOriginatingAddress();
			System.out.println("from" + address);
			System.out.println("message:" + messageBody);

		}
	}

}
