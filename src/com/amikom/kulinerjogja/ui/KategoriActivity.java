
package com.amikom.kulinerjogja.ui;

import com.amikom.kulinerjogja.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class KategoriActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.category_layout);
    }
}
