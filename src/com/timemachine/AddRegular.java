package com.timemachine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import com.timemachine.R;

/**
 * Created with IntelliJ IDEA.
 * User: wanghao
 * Date: 13-1-10
 * Time: 上午6:27
 */
public class AddRegular extends Activity {
    private int CYCLE = 0;
    private EditText t_name;
    private TimePicker tp;
    private CheckBox cb_mon;
    private CheckBox cb_tue;
    private CheckBox cb_wed;
    private CheckBox cb_thur;
    private CheckBox cb_fri;
    private CheckBox cb_sat;
    private CheckBox cb_sun;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_regular);

        tp = (TimePicker) findViewById(R.id.timePicker);
        t_name = (EditText) findViewById(R.id.name);
        Button ok_btn = (Button) findViewById(R.id.add_task_done);
        cb_mon = (CheckBox) findViewById(R.id.is_mon);
        cb_tue = (CheckBox) findViewById(R.id.is_tue);
        cb_wed = (CheckBox) findViewById(R.id.is_wed);
        cb_thur = (CheckBox) findViewById(R.id.is_thur);
        cb_fri = (CheckBox) findViewById(R.id.is_fri);
        cb_sat = (CheckBox) findViewById(R.id.is_sat);
        cb_sun = (CheckBox) findViewById(R.id.is_sun);

        tp.setIs24HourView(true);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        t_name.setText(data.getString("regular_name"));

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb_sun.isChecked())
                    CYCLE += Math.pow(2,0);
                if (cb_mon.isChecked())
                    CYCLE += Math.pow(2, 1);
                if (cb_tue.isChecked())
                    CYCLE += Math.pow(2, 2);
                if (cb_wed.isChecked())
                    CYCLE += Math.pow(2, 3);
                if (cb_thur.isChecked())
                    CYCLE += Math.pow(2, 4);
                if (cb_fri.isChecked())
                    CYCLE += Math.pow(2, 5);
                if (cb_sat.isChecked())
                    CYCLE += Math.pow(2, 6);

                Intent intent = getIntent();
                intent.putExtra("cycle", CYCLE);
                intent.putExtra("name", t_name.getText().toString());
                intent.putExtra("time", tp.getCurrentHour().toString()
                        +":"+tp.getCurrentMinute().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}