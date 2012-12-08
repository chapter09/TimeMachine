package com.timemachine;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.content.Context;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.view.Gravity;
import android.widget.TextView;

public class SettingsActivity extends PreferenceActivity implements	SharedPreferences.OnSharedPreferenceChangeListener {

	private static final String opt_username = "user_name";
	private static final String username = "default";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.tm_settings);
//		PreferenceManager.setDefaultValues(this, R.xml.tm_settings, false);
	}

	public static String getUserName(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getString(opt_username, username);
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
		String key = preference.getKey();
		if( key != null ){
			if(key.equals("about")) {
				Intent intent = new Intent(this, RunActivity.class);
				startActivity(intent);
//				showDialog(1);
			}
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
										  String key) {
		// TODO Auto-generated method stub
		System.out.println("onSharedPreferenceChanged");
		if (key.equals(getString(R.string.hello))){

//			System.out.println(lp.getValue());
		}
	}
}
