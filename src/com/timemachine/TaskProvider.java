package com.timemachine;

import android.content.*;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: wanghao
 * Date: 12-12-21
 * Time: 下午9:46
 */

public class TaskProvider extends ContentProvider {
    private static final int TASKS = 1;
    private static final int TASK = 2;
    private static final int REGULARS = 3;
    private static final int REGULAR = 4;

    private static final UriMatcher uriMatcher
            = new UriMatcher(UriMatcher.NO_MATCH);

    static
    {
        uriMatcher.addURI(Tasks.AUTHORITY, "tasks", TASKS);
        uriMatcher.addURI(Tasks.AUTHORITY, "task/#", TASK);
        uriMatcher.addURI(Regulars.AUTHORITY, "regulars", REGULARS);
        uriMatcher.addURI(Regulars.AUTHORITY, "regular/#", REGULAR);
    }

    private DatabaseHelper dbHelper;
    private static final String DATABASE_NAME = "TimeMachineDataBase";
    private static final String DATABASE_TASK_TABLE = "tasks";
    private static final String DATABASE_REGULAR_TABLE = "regulars";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TASK_TABLE =
            "create table " + DATABASE_TASK_TABLE +
                    " (_id integer primary key autoincrement, "+
                    Tasks.Task.USER_ID +" int, "+
                    Tasks.Task.NAME +" text not null, "+
                    Tasks.Task.STATUS +" int not null, "+
                    Tasks.Task.PRIORITY +" int not null, "+
                    Tasks.Task.CREATE_TIME +" int, "+
                    Tasks.Task.DEADLINE +" text not null, "+
                    Tasks.Task.DONE_TIME +" int, "+
                    Tasks.Task.TYPE +" int, "+
                    Tasks.Task.DIRTY +" int not null, "+
                    Tasks.Task.DONE_WORKLOAD + " text, "+
                    Tasks.Task.WORKLOAD + " text);";

    private static final String CREATE_REGULAR_TABLE =
            "create table " + DATABASE_REGULAR_TABLE +
                    " (_id integer primary key autoincrement, "+
                    Regulars.Regular.USER_ID +" int, "+
                    Regulars.Regular.NAME +" text not null, "+
                    Regulars.Regular.STATUS +" int not null, "+
                    Regulars.Regular.PRIORITY +" int not null, "+
                    Regulars.Regular.CREATE_TIME +" int, "+
                    Regulars.Regular.TIME +" text not null, "+
                    Regulars.Regular.CYCLE +" int not null, "+
                    Regulars.Regular.TYPE +" int, "+
                    Regulars.Regular.DIRTY +" int, "+
                    Regulars.Regular.WORKLOAD + " text);";


    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_TASK_TABLE);
            db.execSQL(CREATE_REGULAR_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            Log.w("Content provider database",
                    "Upgrading database from version " +
                            oldVersion + " to " + newVersion +
                            ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS Todos");
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(this.getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        long id;
        String where;
        int uriType = uriMatcher.match(uri);

        switch (uriType) {
            case TASKS:
                return db.query(DATABASE_TASK_TABLE, projection,
                        selection, selectionArgs, null, null, sortOrder);
            case TASK:
                id = ContentUris.parseId(uri);
                where = Tasks.Task._ID + "=" + id;
                if (selection != null && !"".equals(selection)) {
                    where = where + " and " + selection;
                }
                return db.query(DATABASE_TASK_TABLE, projection, where,
                        selectionArgs, null, null, sortOrder);

            case REGULARS:
                return db.query(DATABASE_REGULAR_TABLE, projection,
                        selection, selectionArgs, null, null, sortOrder);

            case REGULAR:
                id = ContentUris.parseId(uri);
                where = Regulars.Regular._ID + "=" + id;
                if (selection != null && !"".equals(selection)) {
                    where = where + " and " + selection;
                }
                return db.query(DATABASE_REGULAR_TABLE, projection, where,
                        selectionArgs, null, null, sortOrder);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TASKS:
                return "vnd.android.cursor.dir/com.timemachine.task_dict";
            case REGULARS:
                return "vnd.android.cursor.dir/com.timemachine.regular_dict";
            case TASK:
                return "vnd.android.cursor.item/com.timemachine.regular_dict";
            case REGULAR:
                return "vnd.android.cursor.item/com.timemachine.regular_dict";
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int uriType = uriMatcher.match(uri);
        long rowId;

        switch (uriType) {
            case TASKS:
                rowId = db.insert(DATABASE_TASK_TABLE, null, contentValues);
                if (rowId > 0) {
                    Uri taskUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(taskUri, null);
                    return taskUri;
                } else {
                    return null;
                }

            case REGULARS:
                rowId = db.insert(DATABASE_REGULAR_TABLE, null, contentValues);
                if (rowId > 0) {
                    Uri regularUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(regularUri, null);
                    return regularUri;
                } else {
                    return null;
                }

            default:
                throw new SQLException("Fail to insert row into " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count=0;
        long id;

        switch (uriMatcher.match(uri)){
            case TASK:
                count = db.delete(
                        DATABASE_TASK_TABLE,
                        selection,
                        selectionArgs);
                break;
            case TASKS:
                id = ContentUris.parseId(uri);
                count = db.delete(
                        DATABASE_TASK_TABLE,
                        Tasks.Task._ID + " = " + id +
                                (!TextUtils.isEmpty(selection) ? " AND (" +
                                        selection + ')' : ""),
                        selectionArgs);
                break;
            case REGULARS:
                count = db.delete(
                        DATABASE_REGULAR_TABLE,
                        selection,
                        selectionArgs);
                break;
            case REGULAR:
                id = ContentUris.parseId(uri);
                count = db.delete(
                        DATABASE_REGULAR_TABLE,
                        Regulars.Regular._ID + " = " + id +
                                (!TextUtils.isEmpty(selection) ? " AND (" +
                                        selection + ')' : ""),
                        selectionArgs);
                break;

            default: throw new IllegalArgumentException(
                    "Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;        }

    @Override
    public int update(Uri uri, ContentValues contentValues,
                      String selection, String[] selectionArgs) {
        int count = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)){
            case TASKS:
                count = db.update(
                        DATABASE_TASK_TABLE,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            case TASK:
                count = db.update(
                        DATABASE_TASK_TABLE,
                        contentValues,
                        Tasks.Task._ID + " = " + ContentUris.parseId(uri) +
                                (!TextUtils.isEmpty(selection) ? " AND (" +
                                        selection + ')' : ""),
                        selectionArgs);
                break;
            case REGULARS:
                count = db.update(
                        DATABASE_REGULAR_TABLE,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            case REGULAR:
                count = db.update(
                        DATABASE_REGULAR_TABLE,
                        contentValues,
                        Regulars.Regular._ID + " = " + ContentUris.parseId(uri) +
                                (!TextUtils.isEmpty(selection) ? " AND (" +
                                        selection + ')' : ""),
                        selectionArgs);
                break;
            default: throw new IllegalArgumentException(
                    "Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
