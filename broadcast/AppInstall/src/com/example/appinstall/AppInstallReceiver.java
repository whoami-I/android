package com.example.appinstall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AppInstallReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		String action = arg1.getAction();
		if (action.equals("android.intent.action.PACKAGE_INSTALL")) {
			System.out.println("app installed");
		} else if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
			System.out.println("app removed" + arg1.getData());
		} else if (action.equals("android.intent.action.PACKAGE_ADDED")) {
			System.out.println("app added");
		}

	}

}
