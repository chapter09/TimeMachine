package com.timemachine;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TodayActivity extends Activity implements View.OnClickListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.today);
//		ListView listView = getListView();
//
////		Cursor c = managedQuery(Provider.CON)
//
//		View runButton = this.findViewById(R.id.today_run);
//		runButton.setOnClickListener(this);
//
//		TextView tv = new TextView(this);
//		SharedPreferences pre = PreferenceManager.getDefaultSharedPreferences(this);
		super.onCreate(savedInstanceState);

		TextView tv = new TextView(this);
		tv.setText("This is C Activity!");
		tv.setGravity(Gravity.CENTER);
		setContentView(tv);

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
