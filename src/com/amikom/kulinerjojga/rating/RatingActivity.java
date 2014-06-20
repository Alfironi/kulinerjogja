
package com.amikom.kulinerjojga.rating;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.utils.LogManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RatingActivity extends Activity {
    private ListView mListView;
    private List<RatingModel> mItems;
    private RatingAdapter mAdapter;
    private Context mContext;

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
