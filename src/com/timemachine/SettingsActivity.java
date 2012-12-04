package com.timemachine;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.content.Context;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.widget.TextView;

public class SettingsActivity extends PreferenceActivity{

	private static final String opt_username = "user_name";
	private static final String username = "default";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.tm_settings);
	}

	public static String getUserName(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getString(opt_username, username);
	}
	
}
