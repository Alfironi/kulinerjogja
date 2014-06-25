
package com.amikom.kulinerjogja.ui;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.utils.LogManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailKulinerActivity extends Activity implements OnClickListener {
    private String mNama, mAlamat, mDeskripsi, mTelp, mHarga, mJam;
    private double latitude, longitude;
    TextView txtNama, txtAlamat, txtDeskripsi, txtTelp, txtHarga, txtJam,
            status;
    ScrollView scroll;
    ProgressBar mProgressBar;
    private Button mBtnTelf, mBtnLokasi, mBtnBagikan;
    String id;
    RatingBar mRating;
    String user;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_kuliner_layout);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        mSharedPreferences = getSharedPreferences("kkk",
                Context.MODE_PRIVATE);
        user = mSharedPreferences.getString("user", "");
        loadData(id);
        initView();
    }

    private void initView() {
        mRating = (RatingBar) findViewById(R.id.ratingBar);
        txtNama = (TextView) findViewById(R.id.nama);
        txtAlamat = (TextView) findViewById(R.id.alamat);
        txtDeskripsi = (TextView) findViewById(R.id.deskripsi);
        txtTelp = (TextView) findViewById(R.id.telp);
        txtHarga = (TextView) findViewById(R.id.harga);
        txtJam = (TextView) findViewById(R.id.jam);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_detail);
        status = (TextView) findViewById(R.id.status_koneksi_detail);
        scroll = (ScrollView) findViewById(R.id.scroll_detail);
        status.setVisibility(View.GONE);

        mBtnTelf = (Button) findViewById(R.id.telp_detail);
        mBtnLokasi = (Button) findViewById(R.id.lokasi_detail);
        mBtnBagikan = (Button) findViewById(R.id.bagikan_detail);

        mBtnTelf.setOnClickListener(this);
        mBtnLokasi.setOnClickListener(this);
        mBtnBagikan.setOnClickListener(this);
    }

    private void loadData(String id) {
        final String url = "http://jogjakuliner.topmodis.com/restoran/detail/id/"
                + id + "/format/json";
        AsyncHttpClient client = new AsyncHttpClient();

        LogManager.print("call API restoran" + url);

        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // TODO Auto-generated method stub
                LogManager.print("Rating onStart");
                mProgressBar.setVisibility(View.VISIBLE);
                scroll.setVisibility(View.GONE);
            }

            @Override
            public void onFinish() {
                LogManager.print("Rating onFinish");
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                    JSONObject response) {
                scroll.setVisibility(View.VISIBLE);
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
                        latitude = obj.getDouble("lat");
                        longitude = obj.getDouble("longitude");

                        txtNama.setText(": " + mNama);
                        txtAlamat.setText(": " + mAlamat);
                        txtDeskripsi.setText(": " + mDeskripsi);
                        txtTelp.setText(": " + mTelp);
                        txtHarga.setText(": " + mHarga);
                        txtJam.setText(": " + mJam);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                    Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getApplicationContext(),
                        "Proses gagal ada masalah dengan koneksi internet",
                        Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.telp_detail:
                telf();
                break;
            case R.id.lokasi_detail:
                lokasi();
                break;
            case R.id.bagikan_detail:
                submitRating();
                // bagikan();
                break;

            default:
                break;
        }
    }

    private void submitRating() {
        String url = "http://jogjakuliner.topmodis.com/rating/data";

        LogManager.print("submit rating called url = " + url);
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put("uid", "1");
        params.put("id_restoran", id);
        params.put("jumlah_rating", mRating.getRating());
        params.put("name", user);
        params.put("nama_restoran", txtNama.getText().toString());
        LogManager.print("id restoran " + id);
        LogManager.print("jumlah_rating " + mRating.getRating());
        LogManager.print("name" + user);
        LogManager.print("nama_restoran " + txtNama.getText().toString());

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                LogManager.print("onstart");
                mProgressBar.setVisibility(View.VISIBLE);
                scroll.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                LogManager.print("onsucces1");
                Toast.makeText(getApplicationContext(), "Rating berhasil ditambahkan",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinish() {
                LogManager.print("onfinish");
                mProgressBar.setVisibility(View.GONE);
                scroll.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                Toast.makeText(DetailKulinerActivity.this,
                        "Gagal Menyimpan rating, masalah koneksi", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void bagikan() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(
                android.content.Intent.EXTRA_TEXT,
                Html.fromHtml("<p>Nama : " + mNama + ", Alamat : " + mAlamat
                        + ", Deskripsi : " + mDeskripsi + ", Telepon : "
                        + mTelp + ", Harga : " + mHarga + ", Jam : " + mJam
                        + "</p>"));
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    private void lokasi() {
        LogManager.print("latlong sbelum : " + latitude + " | " + longitude);
        Intent intent = new Intent(this, MapAcitivity.class);
        intent.putExtra("lat", String.valueOf(latitude));
        intent.putExtra("long", String.valueOf(longitude));
        startActivity(intent);
    }

    private void telf() {
        String number = txtTelp.getText().toString();
        String phone = "tel:" + number;
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(phone));
        startActivity(callIntent);
    }

}
