
package com.amikom.kulinerjogja.ui;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.utils.Constant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashActivity extends Activity {
    private static int SPLASH_TIME_OUT = 3000;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSharedPreferences = getSharedPreferences(Constant.PREFERENCES_NAME, Context.MODE_PRIVATE);
        setContentView(R.layout.splashscreen_layout);
        
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                finish();
                if (!mSharedPreferences.getBoolean(Constant.IS_LOGIN, false)) {
        			startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        			finish();
        		}else{
        			startActivity(new Intent(SplashActivity.this, MenuActivity.class));
        			finish();
        		}
            }
        }, SPLASH_TIME_OUT);
    }
}
