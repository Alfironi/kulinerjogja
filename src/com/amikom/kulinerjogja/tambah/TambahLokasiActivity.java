
package com.amikom.kulinerjogja.tambah;

import com.amikom.kulinerjogja.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class TambahLokasiActivity extends Activity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_lokasi_kuliner_layout);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_simpan_tambah:
                break;
            case R.id.btn_batal_tambah:
                break;
            default:
                break;
        }
    }
}
