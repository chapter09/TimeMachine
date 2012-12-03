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
	
	private TabHost mTabHost;
	private Intent mAIntent;
	private Intent mBIntent;
	private Intent mCIntent;
	private Intent mDIntent;
	private Intent mEIntent;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.maintabs);
        
        this.mAIntent = new Intent(this,TodayActivity.class);
        this.mBIntent = new Intent(this,RunActivity.class);
        this.mCIntent = new Intent(this,StatisticsActivity.class);
        this.mDIntent = new Intent(this,RegularActivity.class);
        this.mEIntent = new Intent(this,SettingsActivity.class);
        
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
				this.mTabHost.setCurrentTabByTag("Today_TAB");
				break;
			case R.id.radio_button1:
				this.mTabHost.setCurrentTabByTag("Run_TAB");
				break;
			case R.id.radio_button2:
				this.mTabHost.setCurrentTabByTag("Statis_TAB");
				break;
			case R.id.radio_button3:
				this.mTabHost.setCurrentTabByTag("Reg_TAB");
				break;
			case R.id.radio_button4:
				this.mTabHost.setCurrentTabByTag("Settings_TAB");
				break;
			}
		}
		
	}
	
	private void setupIntent() {
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;

		localTabHost.addTab(buildTabSpec("Today_TAB", R.string.menu_today,
				R.drawable.icon_1_n, this.mAIntent));

		localTabHost.addTab(buildTabSpec("Run_TAB", R.string.menu_run,
				R.drawable.icon_2_n, this.mBIntent));

		localTabHost.addTab(buildTabSpec("Statis_TAB",
				R.string.menu_stat, R.drawable.icon_3_n,
				this.mCIntent));

		localTabHost.addTab(buildTabSpec("Reg_TAB", R.string.menu_reg_pub,
				R.drawable.icon_4_n, this.mDIntent));

		localTabHost.addTab(buildTabSpec("Settings_TAB", R.string.menu_settings,
				R.drawable.icon_5_n, this.mEIntent));

	}
	
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return this.mTabHost.newTabSpec(tag).setIndicator(getString(resLabel),
				getResources().getDrawable(resIcon)).setContent(content);
	}
}