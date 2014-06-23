
package com.amikom.kulinerjogja.fragment;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.model.KategoriModel;
import com.amikom.kulinerjogja.ui.DetailKulinerActivity;
import com.amikom.kulinerjogja.utils.KategoriAdapter;
import com.amikom.kulinerjogja.utils.LogManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class KategoriFragment extends Fragment {
    private ListView mList;
    private KategoriAdapter mAdapter;
    private List<KategoriModel> mItems;
    private Context mContext;
    private ProgressBar progressBar;
    private TextView status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View rootviView = inflater.inflate(R.layout.category_layout, container, false);
        mContext = getActivity();
        initView(rootviView);
        Bundle bundle = this.getArguments();
        String kategori = bundle.getString("kategori");

        loadData(kategori);
        return rootviView;
    }

    private void loadData(String kategori) {

        String url = "http://jogjakuliner.topmodis.com/makanan/kategori/name/" + kategori
                + "/format/json";
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
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                LogManager.print("Rating onSuccess : " + response.toString());
                if (mItems.size() > 0) {
                    mItems.clear();
                    mAdapter.notifyDataSetChanged();
                }
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
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                    JSONObject errorResponse) {
                status.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initView(View rootView) {

        mList = (ListView) rootView.findViewById(R.id.listview_category);

        mList = (ListView) rootView.findViewById(R.id.listview_category);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_category);
        status = (TextView) rootView.findViewById(R.id.status_koneksi_category);
        status.setVisibility(View.GONE);
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
        Intent intent = new Intent(getActivity(), DetailKulinerActivity.class);
        intent.putExtra("id", mItems.get(position).getmId());
        startActivity(intent);
    }

}
