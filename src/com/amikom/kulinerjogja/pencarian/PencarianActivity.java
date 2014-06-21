package com.amikom.kulinerjogja.pencarian;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.kategori.KategoriModel;
import com.amikom.kulinerjogja.model.MakananModel;
import com.amikom.kulinerjogja.terdekat.DetailKulinerActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

public class PencarianActivity extends Activity{

	private EditText searchField;
	private ListView listview;
	private ProgressBar progressBar;

	private List<MakananModel> listMakanan;
	private PencarianAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_layout);
		initview();
		getDataMakanan();
	}

	private void initview() {
		searchField = (EditText) findViewById(R.id.field_search);
		listview = (ListView) findViewById(R.id.listview_search);
		progressBar = (ProgressBar) findViewById(R.id.progress_search);
		searchField.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				PencarianActivity.this.adapter.getFilter().filter(s.toString());
				adapter.notifyDataSetChanged();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String idresto = ((MakananModel)adapter.getItem(position)).getIdRestoran();
				goToDetail(idresto);
			}
		});
	}
	private void goToDetail(String id) {
		Log.d("Id", "Id >> "+id);
        Intent intent = new Intent(this, DetailKulinerActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
	private void getDataMakanan() {
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://jogjakuliner.topmodis.com/makanan/data/format/json/";
		client.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onStart() {
				progressBar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onFinish() {
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onSuccess(JSONObject response) {
				listMakanan = new ArrayList<MakananModel>();
				Log.d("Success", "Success : " + response.toString());
				JSONArray array;
				try {
					array = response.getJSONArray("data-list");
					for (int i = 0; i < array.length(); i++) {
						MakananModel model = new MakananModel();
						model.setIdMakanan(array.getJSONObject(i).getString(
								"id_makanan"));
						model.setNamaMakanan(array.getJSONObject(i).getString(
								"nama_makanan"));
						model.setIdKategori(array.getJSONObject(i).getString(
								"id_kategori"));
						model.setIdRestoran(array.getJSONObject(i).getString(
								"id_restoran"));
						model.setNamaRestoran(array.getJSONObject(i).getString(
								"nama_restoran"));
						model.setNamaKategori(array.getJSONObject(i).getString(
								"nama_kategori"));

						listMakanan.add(model);
					}

					adapter = new PencarianAdapter(PencarianActivity.this,
							listMakanan);
					listview.setAdapter(adapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseBody, Throwable e) {
				Log.e("Failure", "Failure : " + statusCode);
			}

		});
	}
}
