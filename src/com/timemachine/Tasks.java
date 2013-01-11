package com.timemachine;

import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: wanghao
 * Date: 13-1-11
 * Time: 下午4:21
 */

public final class Tasks {
    public static final String AUTHORITY
            = "com.timemachine.TaskProvider";
    public static final class Task implements BaseColumns {
        public static final String _ID = "id";
        public static final String USER_ID = "user_id";
        public static final String NAME = "name";
        public static final String CREATE_TIME = "create_time";
        public static final String DONE_TIME = "done_time";
        public static final String PRIORITY = "priority";
        public static final String STATUS = "status";
        public static final String TYPE = "type";
        public static final String WORKLOAD = "workload";
        public static final String DONE_WORKLOAD = "done_workload";
        public static final String DEADLINE = "deadline";
        public static final String DIRTY = "dirty";
        public static final Uri TASKS_URI =
                Uri.parse("content://" + AUTHORITY + "/tasks");
        public static final Uri TASK_URI =
                Uri.parse("content://" + AUTHORITY + "/task");

        public static int getStatus(String deadline) {
            return isToday(deadline)?1:0;
        }
    }
    private static boolean isToday(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date curDate = c.getTime();
        String curDateStr = formatter.format(curDate);
        date = date.split(" ")[0];
        Log.i("Date", curDateStr);
        Log.i("Date", date);
        return curDateStr.equals(date);
    }
}
