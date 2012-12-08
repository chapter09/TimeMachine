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
import android.widget.*;

public class TodayActivity extends ListActivity implements View.OnClickListener {
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
		ListAdapter adapter = createAdapter();
		setListAdapter(adapter);
//		TextView tv = new TextView(this);
//		tv.setText("This is C Activity!");
//		tv.setGravity(Gravity.CENTER);
//		setContentView(tv);

	}

	protected ListAdapter createAdapter()
	{
		String[] testValues = new String[] {
				"Test1",
				"Test2",
				"Test3"
		};

		// Create a simple array adapter (of type string) with the test values
		ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, testValues);

		return adapter;
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
