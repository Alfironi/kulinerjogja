package com.amikom.kulinerjogja.utils;

import android.content.Context;

import com.amikom.kulinerjogja.menu.MenuActivity;

public class Constant {

	public static final String PREFERENCES_NAME = "prepferencesname";
	public static final String IS_LOGIN = "islogin";
	public static final String SERVER_URL = "http://jogjakuliner.topmodis.com/json/";
	public static final String GET_RATING = "rating.php";

	private static double latitude, longitude;
	private static GPSTracker gps;
	public static double getLatitude(Context context){
		gps = new GPSTracker(context);
		latitude = gps.getLatitude();
		return latitude;
	}
	
	public static double getLongitude(Context context){
		gps = new GPSTracker(context);
		longitude = gps.getLongitude();
		return longitude;
	}
}
