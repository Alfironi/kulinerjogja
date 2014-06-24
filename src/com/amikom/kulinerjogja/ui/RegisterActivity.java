package com.amikom.kulinerjogja.ui;

import com.amikom.kulinerjogja.R;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {

	private EditText mUser;
	private EditText mPass;
	private EditText mEmail;
	private EditText mAlamat;
	private RadioButton mLk;
	private RadioButton mPr;
	private RadioGroup mJk;
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
		mEmail = (EditText) findViewById(R.id.email_register);
		mAlamat = (EditText) findViewById(R.id.address_register);
		mLk = (RadioButton) findViewById(R.id.male_radio_register);
		mPr = (RadioButton) findViewById(R.id.female_radio_register);
		mJk = (RadioGroup) findViewById(R.id.jk_register);
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

	/*
	 * Event click button Daftar
	 */
	private void insertUser() {
		if (mUser.getText().toString().equals("")
				|| mPass.getText().toString().equals("")
				|| mEmail.getText().toString().equals("")
				|| mAlamat.getText().toString().equals("")
				|| (mJk.getCheckedRadioButtonId() == -1)) {
			//Jika data yang diinputkan tidak lengkap
			Toast.makeText(this, "Data harus lengkap", Toast.LENGTH_LONG)
					.show();
		} else {
			UserModel model = new UserModel();
			model.setUser(mUser.getText().toString());
			model.setPassword(mPass.getText().toString());
			if (db.insertUser(model)) {
				startActivity(new Intent(this, MenuActivity.class));
				finish();
			} else {
				Toast.makeText(this, "Gagal insert user !!!", Toast.LENGTH_LONG)
						.show();
			}
		}
	}
}
