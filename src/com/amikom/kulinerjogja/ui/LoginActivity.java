package com.amikom.kulinerjogja.ui;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.model.UserModel;
import com.amikom.kulinerjogja.utils.Constant;
import com.amikom.kulinerjogja.utils.DBAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends Activity implements OnClickListener {

	private SharedPreferences mSharedPreferences;
	private SharedPreferences.Editor mEditor;

	private EditText mUserField;
	private EditText mPassField;
	private Button mLoginBtn;
	private Button mRegisterBtn;
	private List<UserModel> userModels;
	private DBAdapter db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_layout);
		db = DBAdapter.getInstance(this);
		userModels = db.selectUser();
		initview();
	}

	private void initview() {
		mUserField = (EditText) findViewById(R.id.email_login);
		mPassField = (EditText) findViewById(R.id.pass_login);
		mLoginBtn = (Button) findViewById(R.id.login_btn);
		mRegisterBtn = (Button) findViewById(R.id.register_btn);
		mRegisterBtn.setOnClickListener(this);
		mLoginBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn:
			login();
			break;
		case R.id.register_btn:
			startActivity(new Intent(this, RegisterActivity.class));
			break;
		default:
			break;
		}
	}

	/*
	 * Event click button Login
	 */
	private void login() {
		if (mUserField.getText().toString().equals("")
				&& mPassField.getText().toString()
				.equals("")) {
			Toast.makeText(this, "Silahkan masukan username dan password !",
					Toast.LENGTH_SHORT).show();
		} else {
			for (int i = 0; i < userModels.size(); i++) {
				UserModel model = userModels.get(i);
				if (mUserField.getText().toString().equals(model.getUser())
						&& mPassField.getText().toString()
								.equals(model.getPassword())) {
					mSharedPreferences = getSharedPreferences(
							Constant.PREFERENCES_NAME, Context.MODE_PRIVATE);
					mEditor = mSharedPreferences.edit();
					mEditor.putBoolean(Constant.IS_LOGIN, true);
					mEditor.commit();
					startActivity(new Intent(this, MenuActivity.class));
					finish();
					return;
				}
			}
			Toast.makeText(this, "Username atau password salah !",
					Toast.LENGTH_SHORT).show();
		}
	}
}
