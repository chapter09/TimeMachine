package com.timemachine;

import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: wanghao
 * Date: 13-1-11
 * Time: 下午4:48
 */
public final class Regulars {
    public static final String AUTHORITY
            = "com.timemachine.TaskProvider";

    public static final class Regular implements BaseColumns {
        public static final String _ID = "id";
        public static final String USER_ID = "user_id";
        public static final String NAME = "name";
        public static final String CREATE_TIME = "create_time";
        public static final String CYCLE = "cycle";
        public static final String PRIORITY = "priority";
        public static final String STATUS = "status";
        public static final String TYPE = "type";
        public static final String WORKLOAD = "workload";
        public static final String DIRTY = "dirty";
        public static final String TIME = "time";
        public static final Uri REGULARS_URI =
                Uri.parse("content://" + AUTHORITY + "/regulars");
        public static final Uri REGULAR_URI =
                Uri.parse("content://" + AUTHORITY + "/regular");

        public static int getStatus(int cycle) {
            Calendar c = Calendar.getInstance();
            c.getTime();
            double day = c.get(Calendar.DAY_OF_WEEK);
            double day_u = Math.pow(2, day);
            Log.v("WEEK", Double.toString(day_u));
            Log.v("WEEK", Integer.toString((int)day_u & cycle));

            if (((int)day_u & cycle) > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
