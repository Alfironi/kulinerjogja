package com.amikom.kulinerjogja.login;

import com.amikom.kulinerjogja.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class LoginActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_layout);
	}
}
