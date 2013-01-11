package com.timemachine;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.*;


public class TodayActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today);
        getContentResolver().registerContentObserver(
                Uri.parse("content://com.timemachine.TaskProvider"), true,
                new TasksObserver(new Handler()));

        ContentResolver cr = getContentResolver();
        Cursor task_cursor = cr.query(Tasks.Task.TASKS_URI,
                null, "status = 1", null, "_id asc");
        startManagingCursor(task_cursor);

        Cursor regular_cursor = cr.query(Regulars.Regular.REGULARS_URI,
                null, "status = 1", null, "_id asc");
        startManagingCursor(regular_cursor);

        ListView taskListView = (ListView) findViewById(R.id.today_task_list);
        ListView regularListView = (ListView) findViewById(R.id.today_regular_list);

        String[] task_from = {Tasks.Task.NAME, Tasks.Task.DEADLINE};
        int[] task_to = {R.id.taskName, R.id.deadline};
        SimpleCursorAdapter task_adapter = new SimpleCursorAdapter(this,
                R.layout.task_entry, task_cursor, task_from, task_to);
        taskListView.setAdapter(task_adapter);

        String[] regular_from = {Regulars.Regular.NAME, Regulars.Regular.CYCLE};
        int[] regular_to = {R.id.taskName, R.id.deadline};
        SimpleCursorAdapter regular_adapter = new SimpleCursorAdapter(this,
                R.layout.task_entry, regular_cursor, regular_from, regular_to);
        regularListView.setAdapter(regular_adapter);
    }

    private final class TasksObserver extends ContentObserver {
        public TasksObserver(Handler handler) {
            super(handler);
        }

        public void onChange(boolean selfChange) {
            Cursor task_cursor = getContentResolver().query(Tasks.Task.TASKS_URI,
                    null, "status = 1", null, "_id asc");
            startManagingCursor(task_cursor);
            ListView taskListView = (ListView) findViewById(R.id.today_task_list);
            String[] task_from = {Tasks.Task.NAME, Tasks.Task.DEADLINE};
            int[] task_to = {R.id.taskName, R.id.deadline};
            SimpleCursorAdapter task_adapter = new SimpleCursorAdapter(getBaseContext(),
                    R.layout.task_entry, task_cursor, task_from, task_to);
            taskListView.setAdapter(task_adapter);

            ListView regularListView = (ListView) findViewById(R.id.today_regular_list);
            Cursor regular_cursor = getContentResolver().query(Regulars.Regular.REGULARS_URI,
                    null, "status = 1", null, "_id asc");
            startManagingCursor(regular_cursor);
            String[] regular_from = {Regulars.Regular.NAME, Regulars.Regular.CYCLE};
            int[] regular_to = {R.id.taskName, R.id.deadline};
            SimpleCursorAdapter regular_adapter = new SimpleCursorAdapter(getBaseContext(),
                    R.layout.task_entry, regular_cursor, regular_from, regular_to);
            regularListView.setAdapter(regular_adapter);
        }
    }
}

