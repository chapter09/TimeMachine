package com.timemachine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.json.*;

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
                String str = "{user_id:1,tasknew:[{description:\"1\",createtime:1,deadline:1},{description:\"1\",createtime:1,deadline:1}]}\n";
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(str);
                    String response = HTTPCommunicate.sendPost(
                        "http://192.168.1.31:8080//TimeMachine/sync",
                        "str="+jsonObject.toString());
                    text.setText(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}