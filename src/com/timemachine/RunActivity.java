package com.timemachine;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.widget.TextView;

public class RunActivity extends Activity{

	TextView tv; //textview to display the countdown
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tv = new TextView(this);
		this.setContentView(tv);
		//5000 is the starting number (in milliseconds)
		//1000 is the number to count down each time (in milliseconds)
		MyCount counter = new MyCount(1500000,1000);
		counter.start();
}

	//countdown timer is an abstract class, so extend it and fill in methods
	public class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			tv.setText("done!");
		}
		@Override
		public void onTick(long millisUntilFinished) {
			tv.setText("Left: " + millisUntilFinished/1000);
		}
	}
}
