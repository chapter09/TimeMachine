package com.timemachine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: wanghao
 * Date: 12-12-24
 * Time: 下午4:16
 */
public class SyncActivity extends Activity {
    Button sync_up_btn;
    EditText text;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sync);

        sync_up_btn = (Button)findViewById(R.id.sync_up);
        text = (EditText)findViewById(R.id.show_up);

        sync_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String response = HTTPCommunicate.sendGet("http://218.193.187.184:3333/TimeMachine/sync", null);
                text.setText(response);
            }
        });


    }
}