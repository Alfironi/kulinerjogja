package com.amikom.kulinerjogja.tambah;

import com.amikom.kulinerjogja.login.LoginActivity;
import com.amikom.kulinerjogja.utils.Constant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

public class HomeActivity extends Activity{
	
	private SharedPreferences mSharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSharedPreferences = getSharedPreferences(Constant.PREFERENCES_NAME, Context.MODE_PRIVATE);
        
        if (!mSharedPreferences.getBoolean(Constant.IS_LOGIN, false)) {
			startActivity(new Intent(this, LoginActivity.class));
			finish();
		}else{
			startActivity(new Intent(this, com.amikom.kulinerjogja.menu.MenuActivity.class));
			finish();
		}
	}
}
