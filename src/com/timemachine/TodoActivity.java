package com.timemachine;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.app.AlertDialog;


/**
 * Created by IntelliJ IDEA.
 * User: haow
 * Date: 12-12-3
 * Time: 下午4:41
 */

/**
 * the database for task-entry should have:
 * 1.name
 * 2.data to occur/finish
 * 3.owner
 * 4.


  */


public class TodoActivity extends Activity {
    private static final int EDIT_DELETE = 1;
    private static final int ADD = 2;
    private ListView list;
    private static final int DIALOG_ALERT = 10;


    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.todo);
        list = (ListView)findViewById(R.id.list);

        Cursor c = managedQuery(TaskProvider.CONTENT_URI , null, null, null, "todo asc");

        String[] from = {
                TaskProvider._ID,
                TaskProvider.TODO,
                TaskProvider.STATE,
                TaskProvider.COLOR
        };

        int[] to = {
                R.id.sqlID,
                R.id.checkBox,
                R.id.checkBox,
                R.id.colorBar
        };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.task_entry, c, from, to);

        adapter.setViewBinder(new TaskViewBinder());
        list.setAdapter(adapter);
        list.setTextFilterEnabled(true);

        list.setOnItemClickListener(
            new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    /// Change in the Content Provider
                    CheckBox cb  = (CheckBox)view.findViewById(R.id.checkBox);
                    TextView tv  = (TextView)view.findViewById(R.id.sqlID);
                    ContentValues values = new ContentValues();

                    /// Toggle Value
                    values.put(TaskProvider.STATE, cb.isChecked() ? 0 : 1 );

                    /// Update it
                    getContentResolver().update(Uri.parse(TaskProvider.CONTENT_URI + "/" + tv.getText()), values, null, null);
                }
            }
        );

        list.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener()
            {
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
                {
                    /// Change in the Content Provider
                    TextView tv = (TextView)view.findViewById(R.id.sqlID);
                    CheckBox cb = (CheckBox)view.findViewById(R.id.checkBox);

                    /// Call the Editor
                    Intent i = new Intent(TodoActivity.this,TaskEditor.class);
                    i.putExtra("todo", cb.getText());
                    i.putExtra("id", tv.getText());
                    i.putExtra("state", "null");
                    startActivityForResult(i, EDIT_DELETE);

                    return true; // if false is returned onItemClick() will do the job
                }
            }
        );

        Button add_btn = (Button) findViewById(R.id.AddButton);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ALERT);
            }
        });
    }



    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ALERT:
                // Create out AlterDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("This will end the activity");
                builder.setCancelable(true);
                builder.setPositiveButton("I agree", new OkOnClickListener());
                builder.setNegativeButton("No, no", new CancelOnClickListener());
                AlertDialog dialog = builder.create();
                dialog.show();
        }
        return super.onCreateDialog(id);
    }

    private final class CancelOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getApplicationContext(), "Activity will continue",
                    Toast.LENGTH_LONG).show();
        }
    }

    private final class OkOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            TodoActivity.this.finish();
        }
    }

}