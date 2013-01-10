package com.timemachine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    private long DEADLINE;
    private int PRIORITY = 0;
    private CheckBox cb;
    private EditText t_name;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_task);

        TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
        DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
        cb = (CheckBox) findViewById(R.id.is_important);
        t_name = (EditText) findViewById(R.id.name);
        Button ok_btn = (Button) findViewById(R.id.add_task_done);

        tp.setIs24HourView(true);
        cb.setChecked(false);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        t_name.setText(data.getString("task_name"));

        //make up the time to the Epoch
        String t_str = Integer.toString(dp.getYear())
                +"-"+Integer.toString(dp.getMonth())
                +"-"+Integer.toString(dp.getDayOfMonth())
                +" "+tp.getCurrentHour().toString()
                +":"+tp.getCurrentMinute().toString();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date gmt = formatter.parse(t_str);
            DEADLINE = gmt.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

