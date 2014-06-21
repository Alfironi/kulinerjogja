
package com.amikom.kulinerjogja.ui;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.model.RatingModel;
import com.amikom.kulinerjogja.utils.LogManager;
import com.amikom.kulinerjogja.utils.RatingAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RatingActivity extends Activity {
    private ListView mListView;
    private List<RatingModel> mItems;
    private RatingAdapter mAdapter;
    private Context mContext;
    private ProgressBar progress;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_layout);
        mContext = this;
        initView();
        getData();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.list_rating);
        mItems = new ArrayList<RatingModel>();
        progress = (ProgressBar) findViewById(R.id.progress_rating);
        status = (TextView) findViewById(R.id.status_koneksi_rating);
        status.setVisibility(View.GONE);
        mAdapter = new RatingAdapter(mContext, mItems);
        mListView.setAdapter(mAdapter);
    }

    private void getData() {
        final String url = "http://jogjakuliner.topmodis.com/rating/data/format/json";
        AsyncHttpClient client = new AsyncHttpClient();

        LogManager.print("call API " + url);

        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // TODO Auto-generated method stub
                LogManager.print("Rating onStart");
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                LogManager.print("Rating onFinish");
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(JSONObject response) {
                LogManager.print("Rating onSuccess : " + response.toString());
                try {
                    JSONArray mJsonArray = response.getJSONArray("data-list");
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        JSONObject obj = mJsonArray.getJSONObject(i);
                        String nomor = String.valueOf(i + 1);
                        String nama = obj.getString("nama_restoran");
                        int rating = Integer.valueOf(obj.getString("jumlah_rating"));

                        RatingModel model = new RatingModel(nomor, nama, rating);
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
            public void onFailure(int statusCode, org.apache.http.Header[] headers,
                    java.lang.String responseBody, java.lang.Throwable e) {
                status.setVisibility(View.VISIBLE);
            }

        });
    }
}
