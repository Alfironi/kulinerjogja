
package com.amikom.kulinerjogja.ui;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.model.UserModel;
import com.amikom.kulinerjogja.utils.DBAdapter;
import com.amikom.kulinerjogja.utils.LogManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
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
    private ProgressBar mProgress;
    private ScrollView mScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register_layout);
        db = DBAdapter.getInstance(this);
        initview();
    }

    private void initview() {
        mProgress = (ProgressBar) findViewById(R.id.progress_register);
        mScroll = (ScrollView) findViewById(R.id.scroll_register);

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
                // Log.d("Register", "Register}}}");
                // insertUser();
                // Log.d("Register", "Register>>>");
                register();
                break;
            default:
                break;
        }
    }

    private void register() {

        if (mUser.getText().toString().equals("")
                || mPass.getText().toString().equals("")
                || mEmail.getText().toString().equals("")
                || mAlamat.getText().toString().equals("")
                || (mJk.getCheckedRadioButtonId() == -1)) {
            // Jika data yang diinputkan tidak lengkap
            Toast.makeText(this, "Data harus lengkap", Toast.LENGTH_LONG)
                    .show();
        } else {
            String urlUser = "http://jogjakuliner.topmodis.com/index.php/users/username/format/json/name/"
                    + mUser.getText().toString();
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(urlUser, new JsonHttpResponseHandler() {

                @Override
                public void onStart() {
                    mProgress.setVisibility(View.VISIBLE);
                    mScroll.setVisibility(View.GONE);
                }

                @Override
                public void onFinish() {
                    mProgress.setVisibility(View.GONE);
                    mScroll.setVisibility(View.VISIBLE);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers,
                        JSONObject response) {
                    try {
                        if (response.getJSONArray("data-list").length() == 0) {
                            submit();
                        } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Username "
                                            + mUser.getText().toString()
                                            + " sudah ada! Silahkan coba yang lain.",
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers,
                        String responseString, Throwable throwable) {
                    Toast.makeText(getApplicationContext(),
                            "Ada masalah koneksi", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private void submit() {
		String url = "http://jogjakuliner.topmodis.com/users/data";
		AsyncHttpClient client = new AsyncHttpClient();

		RequestParams params = new RequestParams();
		params.put("username", mUser.getText().toString());
		params.put("alamat", mAlamat.getText().toString());
		params.put("email", mEmail.getText().toString());
		params.put("encrypted_password", mPass.getText().toString());
		if (mLk.isSelected()) {
			params.put("jenis_kelamin", "Laki-laki");
		} else {
			params.put("jenis_kelamin", "Perempuan");
		}
		client.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onStart() {
				LogManager.print("onstart");
				mProgress.setVisibility(View.VISIBLE);
				mScroll.setVisibility(View.GONE);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				LogManager.print("onsucces1");
				Toast.makeText(getApplicationContext(),
						"Data berhasil ditambahkan", Toast.LENGTH_LONG).show();
				startActivity(new Intent(RegisterActivity.this,
						LoginActivity.class));
				finish();
			}

			@Override
			public void onFinish() {
				LogManager.print("onfinish");
				mProgress.setVisibility(View.GONE);
				mScroll.setVisibility(View.VISIBLE);
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				Toast.makeText(getApplicationContext(),
						"Gagal Menyimpan, masalah koneksi", Toast.LENGTH_LONG)
						.show();
				LogManager.print("error = "+arg3.getMessage());
			}
		});
	}

    @Deprecated
    /*
     * Event click button Daftar(tidak dipakai lagi)
     */
    private void insertUser() {
        if (mUser.getText().toString().equals("")
                || mPass.getText().toString().equals("")
                || mEmail.getText().toString().equals("")
                || mAlamat.getText().toString().equals("")
                || (mJk.getCheckedRadioButtonId() == -1)) {
            // Jika data yang diinputkan tidak lengkap
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
