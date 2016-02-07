package com.kartiks.utility;

import android.util.Log;

public class LoggerGeneral {

	public static final boolean enable = true;
	public static final String TAG = "kartiks custom views";
	
	public static void e(String msg) {
		if (enable) 
			Log.e(TAG,  msg);
		
	}
	
	public static void info(String msg) {
		if (enable) {
			Log.i(TAG, msg);
		}
	}
	
}
