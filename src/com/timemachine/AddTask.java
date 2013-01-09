package com.timemachine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.*;

/**
 * Created with IntelliJ IDEA.
 * User: wanghao
 * Date: 13-1-9
 * Time: 上午12:44
 */
public class AddTask extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_task);

        TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
        EditText t_name = (EditText) findViewById(R.id.task_name);
        Button ok_btn = (Button) findViewById(R.id.add_task_done);

        tp.setIs24HourView(true);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        String str = data.getString("task_name");
        t_name.setText(str);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });
    }
}

