package com.timemachine;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.app.AlertDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by IntelliJ IDEA.
 * User: haow
 * Date: 12-12-3
 * Time: 下午4:41
 */


public class TodoActivity extends Activity {
    private static final int ADD_TASK = 1;
    private static final int ADD_REGULAR = 2;
    private static final int DIALOG_TASK_ADD = 10;

    public void onCreate(Bundle savedInstanceState) {
        ListView listView;
        Button add_btn;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo);
        listView = (ListView)findViewById(R.id.list);

        add_btn = (Button) findViewById(R.id.AddButton);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_TASK_ADD);
            }
        });

//        Cursor c = managedQuery(TaskProvider.CONTENT_URI , null, null, null, "todo asc");
//
//        String[] from = {
//                TaskProvider._ID,
//                TaskProvider.TODO,
//                TaskProvider.STATE,
//        };

//        int[] to = {
//                R.id.sqlID,
//                R.id.checkBox,
//                R.id.checkBox,
//                R.id.colorBar
//        };

//        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.task_entry, c, from, to);

//        adapter.setViewBinder(new TaskViewBinder());
//        listView.setAdapter(adapter);
//        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        /// Change in the Content Provider
//                        CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox);
//                        TextView tv = (TextView) view.findViewById(R.id.sqlID);
//                        ContentValues values = new ContentValues();
//
//                        /// Toggle Value
//                        values.put(TaskProvider.STATE, cb.isChecked() ? 0 : 1);
//
//                        /// Update it
//                        getContentResolver().update(Uri.parse(TaskProvider.CONTENT_URI
//                                + "/" + tv.getText()), values, null, null);
                    }
                }
        );

        listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        /// Change in the Content Provider
//                        TextView tv = (TextView) view.findViewById(R.id.sqlID);
//                        CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox);

                        /// Call the Editor
//                        i.putExtra("todo", cb.getText());
//                        i.putExtra("id", tv.getText());
//                        i.putExtra("state", "null");
//                        startActivityForResult(i, ADD_REGULAR);

                        return true; // if false is returned onItemClick() will do the job
                    }
                }
        );
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        // Create out AlterDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.add_item);

        builder.setPositiveButton(R.string.task, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Bundle data = new Bundle();
                EditText tv = (EditText) findViewById(R.id.name);
                Intent intent = new Intent(TodoActivity.this, AddTask.class);
                String task_name = tv.getText().toString();
                tv.setText("");
                data.putString("task_name", task_name);
                intent.putExtras(data);
                startActivityForResult(intent, ADD_TASK);
            }
        });

        builder.setNegativeButton(R.string.regular, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Bundle data = new Bundle();
                EditText tv = (EditText) findViewById(R.id.name);
                Intent intent = new Intent(TodoActivity.this, AddRegular.class);
                String task_name = tv.getText().toString();
                tv.setText("");
                data.putString("regular_name", task_name);
                intent.putExtras(data);
                startActivityForResult(intent, ADD_REGULAR);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        return super.onCreateDialog(id);
    }

    private final class CancelOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getApplicationContext(), "Activity will continue",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Date dt = new Date();
        if(resultCode == RESULT_OK)
        {
            if(requestCode == ADD_TASK) {
                String deadline = data.getStringExtra("deadline");
                int priority = data.getIntExtra("priority", 0);
                String name = data.getStringExtra("name");

                //get create time from epoch

                Log.i("TEST", deadline);
                Log.i("Priority", Integer.toString(priority));
                Log.i("Name", name);

//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Toast.makeText(TodoActivity.this, name, 8000).show();

                ContentValues values = new ContentValues();
                values.put(Tasks.Task.USER_ID, 0);
                values.put(Tasks.Task.NAME, name);
//                try {
//                    values.put(Tasks.Task.DEADLINE,
//                            formatter.parse(deadline).getTime());
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
                values.put(Tasks.Task.DEADLINE, deadline);
                values.put(Tasks.Task.DONE_TIME, 0);
                values.put(Tasks.Task.DIRTY, 1);
                values.put(Tasks.Task.CREATE_TIME, dt.getTime());
                values.put(Tasks.Task.STATUS,
                        Tasks.Task.getStatus(deadline));
                values.put(Tasks.Task.PRIORITY, priority);
                values.put(Tasks.Task.DONE_TIME, 0);
                values.put(Tasks.Task.WORKLOAD, "");
                values.put(Tasks.Task.DONE_WORKLOAD, "");

                getContentResolver().insert(Tasks.Task.TASKS_URI, values);
            }
            if(requestCode == ADD_REGULAR) {
                //六五四三二一日 : cycle : 二进制
                int cycle = data.getIntExtra("cycle", 0);
                String name = data.getStringExtra("name");
                int priority = data.getIntExtra("priority", 0);
                String time = data.getStringExtra("time");

                ContentValues values = new ContentValues();
                values.put(Regulars.Regular.USER_ID, 0);
                values.put(Regulars.Regular.NAME, name);
                values.put(Regulars.Regular.DIRTY, 1);
                values.put(Regulars.Regular.CREATE_TIME, dt.getTime());
                values.put(Regulars.Regular.STATUS,
                        Regulars.Regular.getStatus(cycle));
                values.put(Regulars.Regular.PRIORITY, priority);
                values.put(Regulars.Regular.WORKLOAD, "");
                values.put(Regulars.Regular.CYCLE, cycle);
                values.put(Regulars.Regular.TIME, time);

                getContentResolver().insert(Regulars.Regular.REGULARS_URI, values);

                Toast.makeText(TodoActivity.this, name, 8000).show();
            }
        }
    }

    private final class OkOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            TodoActivity.this.finish();
        }
    }

}