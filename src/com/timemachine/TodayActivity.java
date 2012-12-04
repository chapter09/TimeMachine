package com.timemachine;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TodayActivity extends Activity implements View.OnClickListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.today);

		View runButton = this.findViewById(R.id.today_run);
		runButton.setOnClickListener(this);

		TextView tv = new TextView(this);
		SharedPreferences pre = PreferenceManager.getDefaultSharedPreferences(this);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.today_run:
				Intent i = new Intent(this, RunActivity.class);
				startActivity(i);
				break;
		}
	}
}
