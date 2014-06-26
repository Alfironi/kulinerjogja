
package com.amikom.kulinerjogja.ui;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.model.UserModel;
import com.amikom.kulinerjogja.utils.Constant;
import com.amikom.kulinerjogja.utils.DBAdapter;
import com.amikom.kulinerjogja.utils.LogManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    private ProgressBar mProgress;
    private LinearLayout mLin;

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
        mProgress = (ProgressBar) findViewById(R.id.progress_login);
        mLin = (LinearLayout) findViewById(R.id.lin_login);

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
                // login();
                userLogin();
                break;
            case R.id.register_btn:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            default:
                break;
        }
    }

    private void userLogin() {
        if (mUserField.getText().toString().equals("")
                && mPassField.getText().toString()
                        .equals("")) {
            Toast.makeText(this, "Silahkan masukan username dan password !",
                    Toast.LENGTH_SHORT).show();
        } else {
            final String url = "http://jogjakuliner.topmodis.com/users/data/format/json";
            AsyncHttpClient client = new AsyncHttpClient();

            LogManager.print("call API " + url);

            client.get(url, new JsonHttpResponseHandler() {

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub
                    LogManager.print("Rating onStart");
                    mProgress.setVisibility(View.VISIBLE);
                    mLin.setVisibility(View.GONE);
                }

                @Override
                public void onFinish() {
                    LogManager.print("Rating onFinish");
                    mProgress.setVisibility(View.GONE);
                    mLin.setVisibility(View.VISIBLE);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    LogManager.print("Rating onSuccess : " + response.toString());
                    try {
                        JSONArray mJsonArray = response.getJSONArray("data-list");
                        boolean isStatus = false;
                        for (int i = 0; i < mJsonArray.length(); i++) {
                            JSONObject obj = mJsonArray.getJSONObject(i);
                            String mUser = obj.getString("username");
                            String mPass = obj.getString("encrypted_password");
                            if (mUserField.getText().toString().equals(mUser)
                                    && mPassField.getText().toString()
                                            .equals(mPass)) {
                                isStatus = true;
                                mSharedPreferences = getSharedPreferences("kkk",
                                        Context.MODE_PRIVATE);
                                mEditor = mSharedPreferences.edit();
                                mEditor.putString("user", mUser);
                                mEditor.commit();
                                startActivity(new Intent(getApplicationContext(),
                                        MenuActivity.class));
                                finish();

                            }
                        }

                        if (isStatus) {

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Username atau password salah !",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                        JSONObject errorResponse) {
                    LogManager.print("onfailure");
                    Toast.makeText(getApplicationContext(), "Ada masalah dengan koneksi",
                            Toast.LENGTH_LONG).show();
                }

            });
        }
    }

    @Deprecated
    /*
     * Event click button Login (tidak dipakai lagi)
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
