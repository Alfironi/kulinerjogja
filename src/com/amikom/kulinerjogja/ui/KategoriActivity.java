
package com.amikom.kulinerjogja.ui;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.model.KategoriModel;
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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class KategoriActivity extends Activity implements OnClickListener {
    private Button mBtnNusantara, mAsia, mLain;
    private ListView mList;
    private KategoriAdapter mAdapter;
    private List<KategoriModel> mItems;
    private Context mContext;
    private String mUrl;
    private ProgressBar progressBar;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.category_layout);
        mContext = this;
        initView();
    }

    private void loadData(String url) {
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

                        KategoriModel model = new KategoriModel(nama, id);
                        mItems.add(model);
                        mAdapter.updateProduct(mItems);
                        mAdapter.notifyDataSetChanged();
                        mAdapter.notifyDataSetInvalidated();
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

    private void initView() {
        mBtnNusantara = (Button) findViewById(R.id.nusantara_category);
        mAsia = (Button) findViewById(R.id.asia_category);
        mLain = (Button) findViewById(R.id.lainnya_category);
        mList = (ListView) findViewById(R.id.listview_category);
        progressBar = (ProgressBar) findViewById(R.id.progress_category);
//        status = ()
        setListener();
        mItems = new ArrayList<KategoriModel>();
        mAdapter = new KategoriAdapter(mContext, mItems);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(listViewListener);
    }

    private void setListener() {
        mBtnNusantara.setOnClickListener(this);
        mAsia.setOnClickListener(this);
        mLain.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nusantara_category:
                if (mItems.size() > 0) {
                    mItems.clear();
                    mAdapter.notifyDataSetChanged();
                }
                mUrl = "http://jogjakuliner.topmodis.com/makanan/kategori/name/nusantara/format/json";
                loadData(mUrl);
                break;
            case R.id.asia_category:
                if (mItems.size() > 0) {
                    mItems.clear();
                    mAdapter.notifyDataSetChanged();
                }
                mUrl = "http://jogjakuliner.topmodis.com/makanan/kategori/name/asia/format/json";
                loadData(mUrl);
                break;
            case R.id.lainnya_category:
                if (mItems.size() > 0) {
                    mItems.clear();
                    mAdapter.notifyDataSetChanged();
                }
                mUrl = "http://jogjakuliner.topmodis.com/makanan/kategori/name/lainnya/format/json";
                loadData(mUrl);
                break;

            default:
                break;
        }
    }
}
