
package com.amikom.kulinerjogja.ui;

import java.util.ArrayList;
import java.util.List;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.model.MakananModel;
import com.amikom.kulinerjogja.utils.PencarianAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListKulinerTerdekatActivity extends Activity {

	private ListView mListView;
	private List<MakananModel> makananModels;
	private PencarianAdapter adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_kuliner_terdekat_layout);
        initview();
    }
    private void initview() {
		mListView = (ListView) findViewById(R.id.list_kuliner_terdekat);
		setdata();
	}
    private void setdata() {
		makananModels = new ArrayList<MakananModel>();
		adapter =  new PencarianAdapter(this, makananModels);
		mListView.setAdapter(adapter);
	}
}
