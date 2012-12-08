package com.timemachine;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.os.Handler;

/**
 * Created by IntelliJ IDEA.
 * User: haow
 * Date: 12-12-7
 * Time: 上午10:51
 */
public class Splash extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);

		/* New Handler to start the Menu-Activity
				 * and close this Splash-Screen after some seconds.*/
		new android.os.Handler().postDelayed(new Runnable(){
			@Override
			public void run() {
				/* Create an Intent that will start the Menu-Activity. */
				Intent mainIntent = new Intent(Splash.this, MainTabActivity.class);
				Splash.this.startActivity(mainIntent);
				Splash.this.finish();
			}
		}, 3000);
	}
}

