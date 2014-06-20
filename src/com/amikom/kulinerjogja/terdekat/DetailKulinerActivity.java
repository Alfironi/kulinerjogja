
package com.amikom.kulinerjogja.terdekat;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.utils.LogManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class DetailKulinerActivity extends Activity {
    private String mNama, mAlamat, mDeskripsi, mTelp, mHarga, mJam;
    TextView txtNama, txtAlamat, txtDeskripsi, txtTelp, txtHarga, txtJam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_kuliner_layout);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        loadData(id);
        initView();
    }

    private void initView() {
        txtNama = (TextView) findViewById(R.id.nama);
        txtAlamat = (TextView) findViewById(R.id.alamat);
        txtDeskripsi = (TextView) findViewById(R.id.deskripsi);
        txtTelp = (TextView) findViewById(R.id.telp);
        txtHarga = (TextView) findViewById(R.id.harga);
        txtJam = (TextView) findViewById(R.id.jam);
        
        
    }

    private void loadData(String id) {
        final String url = "http://jogjakuliner.topmodis.com/restoran/detail/id/" + id
                + "/format/json";
        AsyncHttpClient client = new AsyncHttpClient();

        LogManager.print("call API " + url);

        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // TODO Auto-generated method stub
                LogManager.print("Rating onStart");

            }

            @Override
            public void onFinish() {
                LogManager.print("Rating onFinish");
            }

            @Override
            public void onSuccess(JSONObject response) {
                LogManager.print("Rating onSuccess : " + response.toString());
                try {
                    JSONArray mJsonArray = response.getJSONArray("data-list");
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        JSONObject obj = mJsonArray.getJSONObject(i);
                        mNama = obj.getString("nama_restoran");
                        mAlamat = obj.getString("alamat_restoran");
                        mDeskripsi = obj.getString("deskripsi");
                        mTelp = obj.getString("telp");
                        mHarga = obj.getString("harga");
                        mJam = obj.getString("jambuka");
                        
                        txtNama.setText(": "+mNama);
                        txtAlamat.setText(": "+mAlamat);
                        txtDeskripsi.setText(": "+mDeskripsi);
                        txtTelp.setText(": "+mTelp);
                        txtHarga.setText(": "+mHarga);
                        txtJam.setText(": "+mJam);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable e, JSONObject errorResponse) {
                LogManager.print("This on failure 1 : " + errorResponse.toString());
            }

            @Override
            public void onFailure(Throwable e, JSONArray errorResponse) {
                LogManager.print("This on failure 2 : " + errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers,
                    java.lang.String responseBody, java.lang.Throwable e) {
                LogManager.print("This on failure 3 : " + responseBody);
                if (statusCode == 404) {
                    LogManager.print("400");
                } else if (statusCode == 500) {
                    LogManager.print("500");
                } else if (statusCode == 0) {
                    LogManager.print("0");
                } else {
                    LogManager.print("fail");
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                // TODO Auto-generated method stub
                super.onFailure(arg0, arg1, arg2, arg3);
                LogManager.print("This on failure 3 : ");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e,
                    JSONArray errorResponse) {
                LogManager.print("This on failure 4 : ");
            };

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e,
                    JSONObject errorResponse) {
                // TODO Auto-generated method stub
                super.onFailure(statusCode, headers, e, errorResponse);
                LogManager.print("This on failure 5 : ");
            }

            @Override
            public void onFailure(int statusCode, Throwable e, JSONArray errorResponse) {
                // TODO Auto-generated method stub
                super.onFailure(statusCode, e, errorResponse);
                LogManager.print("This on failure 6 : ");
            }

            @Override
            public void onFailure(int statusCode, Throwable e, JSONObject errorResponse) {
                // TODO Auto-generated method stub
                super.onFailure(statusCode, e, errorResponse);
                LogManager.print("This on failure 7 : ");
            }

            @Override
            public void onFailure(String responseBody, Throwable error) {
                // TODO Auto-generated method stub
                super.onFailure(responseBody, error);
                LogManager.print("This on failure 8 : ");
            }
        });
    }
}
