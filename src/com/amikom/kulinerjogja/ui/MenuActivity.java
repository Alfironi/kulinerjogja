
package com.amikom.kulinerjogja.ui;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.utils.GPSTracker;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MenuActivity extends Activity implements OnClickListener {

    private LinearLayout mSearch, mNear, mKategori, mRating, mTambah;
    private TextView textAddress;
    private Geocoder geocoder;
    private List<Address> address;
    private String add, city, country;
    private double latitude, longitude;
    private GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_layout);
        gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        initView();
        setListener();
        new LoadLocation().execute();
    }

    private void setListener() {
        mSearch.setOnClickListener(this);
        mNear.setOnClickListener(this);
        mKategori.setOnClickListener(this);
        mRating.setOnClickListener(this);
        mTambah.setOnClickListener(this);
    }

    private void initView() {
        mSearch = (LinearLayout) findViewById(R.id.search_menu_layout);
        mNear = (LinearLayout) findViewById(R.id.near_menu_layout);
        mKategori = (LinearLayout) findViewById(R.id.category_menu_layout);
        mRating = (LinearLayout) findViewById(R.id.rating_menu_layout);
        mTambah = (LinearLayout) findViewById(R.id.add_menu_layout);
        textAddress = (TextView) findViewById(R.id.near_distance_menu_layout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_menu_layout:
                startActivity(new Intent(MenuActivity.this, PencarianActivity.class));
                break;
            case R.id.near_menu_layout:
                startActivity(new Intent(MenuActivity.this,
                        KulinerTerdekatActivity.class));
                break;
            case R.id.category_menu_layout:
                startActivity(new Intent(MenuActivity.this, TabKategoriActivity.class));
                break;
            case R.id.rating_menu_layout:
                startActivity(new Intent(MenuActivity.this, RatingActivity.class));
                break;
            case R.id.add_menu_layout:
                startActivity(new Intent(MenuActivity.this,
                        TambahLokasiActivity.class));
                break;
            default:
                break;
        }
    }

    private class LoadLocation extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            geocoder = new Geocoder(MenuActivity.this, Locale.getDefault());
            try {
                address = geocoder.getFromLocation(latitude, longitude, 1);
                // address = geocoder.getFromLocation(-6.2192001,106.8201898,
                // 1);
                add = address.get(0).getAddressLine(0);
                city = address.get(0).getAddressLine(1);
                country = address.get(0).getAddressLine(2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (add != null) {
                textAddress.setText("Anda berada di dekat : " + add + " " + city
                        + " " + country);
            } else {
                textAddress
                        .setText("Posisi Anda gagal diload. Ada masalah dengan koneksi GPS.");
            }
            super.onPostExecute(result);
        }

    }

}
