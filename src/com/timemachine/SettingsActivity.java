package com.timemachine;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Gravity;
import android.widget.TextView;

public class SettingsActivity extends PreferenceActivity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.tm_settings);
//		TextView tv = new TextView(this);
//		tv.setText("This is More Activity!");
//		tv.setGravity(Gravity.CENTER);
//
//
//		setContentView(tv);
	}
	
}
