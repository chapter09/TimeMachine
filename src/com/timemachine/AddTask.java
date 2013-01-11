package com.timemachine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: wanghao
 * Date: 13-1-9
 * Time: 上午12:44
 */
public class AddTask extends Activity {
    private String DEADLINE;
    private int PRIORITY = 0;
    private CheckBox cb;
    private EditText t_name;
    private TimePicker tp;
    private DatePicker dp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_task);

        tp = (TimePicker) findViewById(R.id.timePicker);
        dp = (DatePicker) findViewById(R.id.datePicker);
        cb = (CheckBox) findViewById(R.id.is_important);
        t_name = (EditText) findViewById(R.id.name);
        Button ok_btn = (Button) findViewById(R.id.add_task_done);

        tp.setIs24HourView(true);
        cb.setChecked(false);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        t_name.setText(data.getString("task_name"));

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String monthStr = (dp.getMonth()+1 < 10)
                        ? "0" + Integer.toString(dp.getMonth()+1)
                        : Integer.toString(dp.getMonth()+1);

                String dayStr = (dp.getDayOfMonth() < 10)
                        ? "0" + Integer.toString(dp.getDayOfMonth())
                        : Integer.toString(dp.getDayOfMonth());

                //make up the time to the Epoch
                DEADLINE = Integer.toString(dp.getYear())
                        +"-"+monthStr
                        +"-"+dayStr
                        +" "+tp.getCurrentHour().toString()
                        +":"+tp.getCurrentMinute().toString();

                Log.i("SetDate", DEADLINE);

                if (cb.isChecked()) {
                    PRIORITY = 1;
                }
                Intent intent = getIntent();
                intent.putExtra("deadline", DEADLINE);
                intent.putExtra("priority", PRIORITY);
                intent.putExtra("name", t_name.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}

