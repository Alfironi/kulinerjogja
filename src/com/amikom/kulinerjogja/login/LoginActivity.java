package com.amikom.kulinerjogja.login;

import com.amikom.kulinerjogja.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener{

	private EditText mEmailField;
	private EditText mPassField;
	private Button mLoginBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_layout);
		initview();
	}
	
	private void initview() {
		mEmailField = (EditText) findViewById(R.id.email_login);
		mPassField = (EditText) findViewById(R.id.pass_login);
		mLoginBtn = (Button) findViewById(R.id.login_btn);
		mLoginBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == mLoginBtn.getId()) {
			login();
		}
	}
	
	private void login() {
	}
}
