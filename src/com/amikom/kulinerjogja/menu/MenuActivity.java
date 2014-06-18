
package com.amikom.kulinerjogja.menu;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.tambah.TambahLokasiActivity;
import com.amikom.kulinerjogja.terdekat.KulinerTerdekatActivity;
import com.amikom.kulinerjogja.ui.KategoriActivity;
import com.amikom.kulinerjogja.ui.PencarianActivity;
import com.amikom.kulinerjogja.ui.RatingActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;

public class MenuActivity extends Activity implements OnClickListener {
    private LinearLayout mSearch, mNear, mKategori, mRating, mTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_layout);
        initView();
        setListener();
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
                startActivity(new Intent(MenuActivity.this, KategoriActivity.class));
                break;
            case R.id.rating_menu_layout:
                startActivity(new Intent(MenuActivity.this, RatingActivity.class));
                break;
            case R.id.add_menu_layout:
                startActivity(new Intent(MenuActivity.this, TambahLokasiActivity.class));
                break;
            default:
                break;
        }
    }
}
