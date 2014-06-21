
package com.amikom.kulinerjogja.ui;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.fragment.KategoriFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TextView;

public class TabKategoriActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_kategori_layout);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        Bundle bundle1 = new Bundle();
        bundle1.putString("kategori", "nusantara");
        Bundle bundle2 = new Bundle();
        bundle2.putString("kategori", "asia");
        Bundle bundle3 = new Bundle();
        bundle3.putString("kategori", "lainnya");

        mTabHost.addTab(mTabHost.newTabSpec("Nusantara").setIndicator("Nusantara"),
                KategoriFragment.class, bundle1);
        mTabHost.addTab(mTabHost.newTabSpec("Asia").setIndicator("Asia"),
                KategoriFragment.class, bundle2);
        mTabHost.addTab(mTabHost.newTabSpec("Lainnya").setIndicator("Lainnya"),
                KategoriFragment.class, bundle3);

        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++)
        {
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i)
                    .findViewById(android.R.id.title); // Unselected Tabs
            tv.setTextColor(Color.parseColor("#ffffff"));
        }

    }

    // private Intent goToKategori(String tab){
    //
    // Intent intent = new Intent(TabKategoriActivity.this,
    // KategoriActivity.class);
    // intent.putExtra("kategori", tab);
    // return intent;
    //
    // }
}
