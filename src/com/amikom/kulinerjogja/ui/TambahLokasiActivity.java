package com.amikom.kulinerjogja.ui;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.utils.LogManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class TambahLokasiActivity extends Activity implements OnClickListener {
	private Button mBtnSimpan, mBtnBatal;
	private TextView mNama, mAlamat, mDeskripsi, mTelp, mHarga, mJam;
	private String mLat, mLong;

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

		client.post(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onStart() {
				LogManager.print("onstart");
			}

			@Override
			public void onFinish() {
			}

			@Override
			public void onSuccess(JSONObject response) {
				LogManager.print("onsucces " + response);

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseBody, Throwable e) {
				super.onFailure(statusCode, headers, responseBody, e);
				LogManager.print("Failure : " + statusCode + " : "
						+ responseBody);
				Toast.makeText(TambahLokasiActivity.this,
						"Gagal Menyimpan, masalah koneksi", Toast.LENGTH_LONG)
						.show();

			}
		});
	}

	private void generateLatLong(String strAddress) {
		Geocoder coder = new Geocoder(this);
		List<Address> address;

		try {
			address = coder.getFromLocationName(strAddress, 5);

			if (address == null) {
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
