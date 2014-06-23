
package com.amikom.kulinerjogja.ui;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.utils.LogManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class TambahLokasiActivity extends Activity implements OnClickListener {
    private Button mBtnSimpan, mBtnBatal;
    private TextView mNama, mAlamat, mDeskripsi, mTelp, mHarga, mJam;
    private String mLat, mLong;
    private ProgressBar mProgress;
    private ScrollView mScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_lokasi_kuliner_layout);
        initView();
    }

    private void initView() {
        mBtnSimpan = (Button) findViewById(R.id.btn_simpan_tambah);
        mBtnBatal = (Button) findViewById(R.id.btn_batal_tambah);
        mNama = (TextView) findViewById(R.id.nama_tambah);
        mAlamat = (TextView) findViewById(R.id.alamat_tambah);
        mDeskripsi = (TextView) findViewById(R.id.desc_tambah);
        mTelp = (TextView) findViewById(R.id.telf_tambah);
        mHarga = (TextView) findViewById(R.id.harga_tambah);
        mJam = (TextView) findViewById(R.id.jam_tambah);
        mBtnSimpan.setOnClickListener(this);
        mBtnBatal.setOnClickListener(this);

        mProgress = (ProgressBar) findViewById(R.id.progress_tambah);
        mScroll = (ScrollView) findViewById(R.id.scrol_tambah);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_simpan_tambah:
                simpan();
                break;
            case R.id.btn_batal_tambah:
                batal();
                break;
            default:
                break;
        }
    }

    private void batal() {

        onBackPressed();
    }

    private void simpan() {
        if (mNama.getText().toString().equals("")) {
            Toast.makeText(this, "Silahkan inputkan nama", Toast.LENGTH_SHORT).show();
            mNama.requestFocus();
        } else if (mAlamat.getText().toString().equals("")) {
            Toast.makeText(this, "Silahkan inputkan alamat", Toast.LENGTH_SHORT).show();
            mAlamat.requestFocus();
        } else if (mDeskripsi.getText().toString().equals("")) {
            Toast.makeText(this, "Silahkan inputkan deskripsi", Toast.LENGTH_SHORT).show();
            mDeskripsi.requestFocus();
        } else if (mTelp.getText().toString().equals("")) {
            Toast.makeText(this, "Silahkan inputkan telf", Toast.LENGTH_SHORT).show();
            mTelp.requestFocus();
        } else if (mJam.getText().toString().equals("")) {
            Toast.makeText(this, "Silahkan inputkan jam buka", Toast.LENGTH_SHORT).show();
            mJam.requestFocus();
        } else if (mHarga.getText().toString().equals("")) {
            Toast.makeText(this, "Silahkan inputkan harga", Toast.LENGTH_SHORT).show();
            mHarga.requestFocus();
        } else {
            generateLatLong(mAlamat.getText().toString());
            String url = "http://jogjakuliner.topmodis.com/restoran/data";
            AsyncHttpClient client = new AsyncHttpClient();

            RequestParams params = new RequestParams();
            params.put("nama_restoran", mNama.getText().toString());
            params.put("alamat_restoran", mAlamat.getText().toString());
            params.put("deskripsi", mDeskripsi.getText().toString());
            params.put("telp", mTelp.getText().toString());
            params.put("jambuka", mJam.getText().toString());
            params.put("harga", mHarga.getText().toString());
            params.put("lat", mLat);
            params.put("longitude", mLong);
            params.put("foto", "");

            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    LogManager.print("onstart");
                    mProgress.setVisibility(View.VISIBLE);
                    mScroll.setVisibility(View.GONE);
                }

                @Override
                public void onFinish() {
                    LogManager.print("onfinish");
                    mProgress.setVisibility(View.GONE);
                    mScroll.setVisibility(View.VISIBLE);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    LogManager.print("onsucces " + response);
                    Toast.makeText(getApplicationContext(), "Data berhasil ditambahkan",
                            Toast.LENGTH_LONG).show();
                    clearForm();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse,
                        Throwable e) {
                    Toast.makeText(TambahLokasiActivity.this,
                            "Gagal Menyimpan, masalah koneksi", Toast.LENGTH_LONG)
                            .show();
                }

            });
        }

    }

    private void clearForm() {
        mNama.setText("");
        mAlamat.setText("");
        mDeskripsi.setText("");
        mTelp.setText("");
        mJam.setText("");
        mHarga.setText("");
    }

    private void generateLatLong(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 5);

            if (address.size() == 0) {
                Toast.makeText(TambahLokasiActivity.this,
                        "Gagal Menyimpan, alamat tidak dikenali google map", Toast.LENGTH_LONG)
                        .show();
                mLat = "0";
                mLong = "0";
            } else {
                Address location = address.get(0);
                mLat = String.valueOf(location.getLatitude());
                mLong = String.valueOf(location.getLongitude());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
