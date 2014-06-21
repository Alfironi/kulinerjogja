
package com.amikom.kulinerjogja.ui;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.model.KategoriModel;
import com.amikom.kulinerjogja.utils.Constant;
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

import java.util.ArrayList;
import java.util.List;

public class ListKulinerTerdekatActivity extends Activity {
    private String mNama, mAlamat, mDeskripsi, mTelp, mHarga, mJam, mLat, mLong;
    private ListView mList;
    private KategoriAdapter mAdapter;
    private List<KategoriModel> mItems;
    private Context mContext;
    private String jarak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_kuliner_terdekat_layout);
        jarak = getIntent().getStringExtra("jarak");
        mContext = this;
        initView();
        loadData();
    }

    private void initView() {
        mList = (ListView) findViewById(R.id.list_kuliner_terdekat);
        mItems = new ArrayList<KategoriModel>();
        mAdapter = new KategoriAdapter(mContext, mItems);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(listViewListener);
    }

    private final OnItemClickListener listViewListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
                        String nama = obj.getString("nama_makanan");
                        String id = obj.getString("id_restoran");
                        mLat = obj.getString("lat");
                        mLong = obj.getString("Longitude");
                        if (getDistance(mLat, mLong, jarak)) {
                            KategoriModel model = new KategoriModel(nama, id);
                            mItems.add(model);
                            mAdapter.updateProduct(mItems);
                            mAdapter.notifyDataSetChanged();
                            mAdapter.notifyDataSetInvalidated();
                        }

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

    private boolean getDistance(String latB, String lngB, String jarak) {
        boolean isStatus = true;
        Location locationA = new Location("point A");

        locationA.setLatitude(Constant.getLatitude(mContext));
        locationA.setLongitude(Constant.getLongitude(mContext));

        Location locationB = new Location("point B");

        locationB.setLatitude(Double.parseDouble(latB));
        locationB.setLongitude(Double.parseDouble(lngB));

        double distance = Double.valueOf(jarak);
        double dist = locationA.distanceTo(locationB);

        LogManager.print("lat a = " + Constant.getLatitude(mContext));
        LogManager.print("long a = " + Constant.getLongitude(mContext));

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
