package com.amikom.kulinerjogja.terdekat;

import com.amikom.kulinerjogja.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class KulinerTerdekatActivity extends Activity implements
		OnClickListener {
	private TextView m100, m200, m300;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kuliner_terdekat_layout);
		initView();
	}

	private void initView() {
		m100 = (TextView) findViewById(R.id.dist_100);
		m200 = (TextView) findViewById(R.id.dist_200);
		m300 = (TextView) findViewById(R.id.dist_300);

		m100.setOnClickListener(this);
		m200.setOnClickListener(this);
		m300.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
