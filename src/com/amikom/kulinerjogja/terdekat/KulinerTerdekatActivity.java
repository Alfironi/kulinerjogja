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
	private TextView m100;
	private TextView m200;
	private TextView m300;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kuliner_terdekat_layout);
		initview();
	}

	private void initview() {
		m100 = (TextView) findViewById(R.id.m100);
		m200 = (TextView) findViewById(R.id.m200);
		m300 = (TextView) findViewById(R.id.m300);
		m100.setOnClickListener(this);
		m200.setOnClickListener(this);
		m300.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.m100:
			click100();
			break;
		case R.id.m200:
			click200();
			break;
		case R.id.m300:
			click300();
			break;
		default:
			break;
		}
	}

	private void click100() {
	}

	private void click200() {
	}

	private void click300() {
	}
}
