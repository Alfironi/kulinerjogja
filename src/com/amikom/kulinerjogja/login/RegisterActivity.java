package com.amikom.kulinerjogja.login;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.menu.MenuActivity;
import com.amikom.kulinerjogja.model.UserModel;
import com.amikom.kulinerjogja.utils.DBAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener{

	private EditText mUser;
	private EditText mPass;
	private Button mDaftar;
	private DBAdapter db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_layout);
		db = DBAdapter.getInstance(this);
		initview();
	}
	
	private void initview() {
		mUser = (EditText) findViewById(R.id.username_register);
		mPass = (EditText) findViewById(R.id.password_register);
		mDaftar = (Button) findViewById(R.id.register);
		mDaftar.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register:
			Log.d("Register", "Register}}}");
			insertUser();
			Log.d("Register", "Register>>>");
			break;
		default:
			break;
		}
	}
	private void insertUser() {
		UserModel model = new UserModel();
		model.setUser(mUser.getText().toString());
		model.setPassword(mPass.getText().toString());
		if (db.insertUser(model)) {
			startActivity(new Intent(this, MenuActivity.class));
			finish();			
		}else {
			Toast.makeText(this, "Gagal insert user !!!", Toast.LENGTH_LONG).show();
		}
	}
}
