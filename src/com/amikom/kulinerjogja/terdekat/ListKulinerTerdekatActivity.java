
package com.amikom.kulinerjogja.terdekat;

import com.amikom.kulinerjogja.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListKulinerTerdekatActivity extends Activity {
    private ListView mList;
    private ArrayAdapter<String> mAdapter;
    private final String[] mItem = {
            "a", "b", "c"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_kuliner_terdekat_layout);
        mList = (ListView) findViewById(R.id.list_kuliner_terdekat);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mItem);
        mList.setAdapter(mAdapter);
    }
}
