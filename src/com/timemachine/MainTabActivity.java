package com.timemachine;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TabHost;

public class MainTabActivity extends TabActivity implements OnCheckedChangeListener{
	
	private TabHost tmTabHost;
	private Intent todayIntent;
	private Intent todoIntent;
	private Intent statIntent;
	private Intent regIntent;
	private Intent setIntent;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.maintabs);
        
        this.todayIntent = new Intent(this,TodayActivity.class);
        this.todoIntent = new Intent(this,TodoActivity.class);
        this.statIntent = new Intent(this,StatisticsActivity.class);
        this.regIntent = new Intent(this,RegularActivity.class);
        this.setIntent = new Intent(this,SettingsActivity.class);
        
		((RadioButton) findViewById(R.id.radio_button0))
		.setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button1))
		.setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button2))
		.setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button3))
		.setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button4))
		.setOnCheckedChangeListener(this);

        setupIntent();
    }

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked){
			switch (buttonView.getId()) {
			case R.id.radio_button0:
				this.tmTabHost.setCurrentTabByTag("Today_TAB");
				break;
			case R.id.radio_button1:
				this.tmTabHost.setCurrentTabByTag("Run_TAB");
				break;
			case R.id.radio_button2:
				this.tmTabHost.setCurrentTabByTag("Statis_TAB");
				break;
			case R.id.radio_button3:
				this.tmTabHost.setCurrentTabByTag("Reg_TAB");
				break;
			case R.id.radio_button4:
				this.tmTabHost.setCurrentTabByTag("Settings_TAB");
				break;
			}
		}
	}
	
	private void setupIntent() {
		this.tmTabHost = getTabHost();
		TabHost localTabHost = this.tmTabHost;

		localTabHost.addTab(buildTabSpec("Today_TAB", R.string.menu_today,
				R.drawable.icon_1_n, this.todayIntent));

		localTabHost.addTab(buildTabSpec("Run_TAB", R.string.menu_todo,
				R.drawable.icon_2_n, this.todoIntent));

		localTabHost.addTab(buildTabSpec("Statis_TAB",
				R.string.menu_stat, R.drawable.icon_3_n,
				this.statIntent));

		localTabHost.addTab(buildTabSpec("Reg_TAB", R.string.menu_reg_pub,
				R.drawable.icon_4_n, this.regIntent));

		localTabHost.addTab(buildTabSpec("Settings_TAB", R.string.menu_settings,
				R.drawable.icon_5_n, this.setIntent));

	}
	
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return this.tmTabHost.newTabSpec(tag).setIndicator(getString(resLabel),
				getResources().getDrawable(resIcon)).setContent(content);
	}
}