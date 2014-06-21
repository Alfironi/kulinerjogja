package com.amikom.kulinerjogja.ui;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.model.KategoriModel;
import com.amikom.kulinerjogja.utils.Constant;
import com.amikom.kulinerjogja.utils.GPSTracker;
import com.amikom.kulinerjogja.utils.KategoriAdapter;
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
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListKulinerTerdekatActivity extends Activity {
	private String mNama, mAlamat, mDeskripsi, mTelp, mHarga, mJam, mLat,
			mLong;
	private ProgressBar progressBar;
	private TextView status;
	private ListView mList;
	private KategoriAdapter mAdapter;
	private List<KategoriModel> mItems;
	private Context mContext;
	private String jarak;
	private double latitude, longitude;
	private GPSTracker gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_kuliner_terdekat_layout);
		jarak = getIntent().getStringExtra("distance");
		mContext = this;
		gps = new GPSTracker(this);
		latitude = gps.getLatitude();
		longitude = gps.getLongitude();
		LogManager.print("Jarak : "+jarak);
		initView();
		loadData();
	}

	private void initView() {
		mList = (ListView) findViewById(R.id.list_kuliner_terdekat);
		mItems = new ArrayList<KategoriModel>();
		mAdapter = new KategoriAdapter(mContext, mItems);
		progressBar = (ProgressBar) findViewById(R.id.progress_listterdekat);
		status = (TextView) findViewById(R.id.status_koneksi_listterdekat);
		status.setVisibility(View.GONE);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(listViewListener);
	}

	private final OnItemClickListener listViewListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			goToDetail(mItems, position);
		}

	};

	private void goToDetail(List<KategoriModel> mItems, int position) {
		Intent intent = new Intent(this, DetailKulinerActivity.class);
		intent.putExtra("id", mItems.get(position).getmId());
		startActivity(intent);
	}

	private void loadData() {
		final String url = "http://jogjakuliner.topmodis.com/restoran/data/format/json";
		AsyncHttpClient client = new AsyncHttpClient();

		LogManager.print("call API " + url);

		client.get(url, new JsonHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				LogManager.print("Rating onStart");
				progressBar.setVisibility(View.VISIBLE);

			}

			@Override
			public void onFinish() {
				LogManager.print("Rating onFinish");
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onSuccess(JSONObject response) {
				LogManager.print("Rating onSuccess : " + response.toString());
				try {
					JSONArray mJsonArray = response.getJSONArray("data-list");
					for (int i = 0; i < mJsonArray.length(); i++) {
						JSONObject obj = mJsonArray.getJSONObject(i);
						String nama = obj.getString("nama_restoran");
						String id = obj.getString("id_restoran");
						mLat = obj.getString("lat");
						mLong = obj.getString("longitude");
						if (getDistance(mLat, mLong, jarak)) {
							KategoriModel model = new KategoriModel(nama, id);
							mItems.add(model);
							mAdapter.updateProduct(mItems);
							mAdapter.notifyDataSetChanged();
							mAdapter.notifyDataSetInvalidated();
						}
						if (mItems.size() == 0) {
							status.setVisibility(View.VISIBLE);
							status.setText("Tidak ada Kuliner terdekat dalam jarak "+ jarak+" m");
						}

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode,
					org.apache.http.Header[] headers,
					java.lang.String responseBody, java.lang.Throwable e) {
				status.setVisibility(View.VISIBLE);
				status.setText("Gagal load data, ada masalah dengan Koneksi !");
			}

		});
	}

	private boolean getDistance(String latB, String lngB, String jarak) {
		boolean isStatus = true;
		Location locationA = new Location("point A");

		locationA.setLatitude(latitude);
		locationA.setLongitude(longitude);

		Location locationB = new Location("point B");

		locationB.setLatitude(Double.parseDouble(latB));
		locationB.setLongitude(Double.parseDouble(lngB));

		double distance = Double.valueOf(jarak);
		double dist = locationA.distanceTo(locationB);

		LogManager.print("lat a = " + latitude);
		LogManager.print("long a = " + longitude);

		LogManager.print("lat b = " + latB);
		LogManager.print("long b = " + lngB);

		LogManager.print("jarak = " + dist);
		LogManager.print("jarak patokan =" + distance);
		if (dist <= distance) {
			isStatus = true;
		} else {
			isStatus = false;
		}
		return isStatus;
	}

}
