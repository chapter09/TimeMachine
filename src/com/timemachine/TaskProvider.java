package com.timemachine;

import android.content.*;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
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
    public static final String PROVIDER_NAME = "com.timemachine.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://"+ PROVIDER_NAME + "/todos");

    public static final String _ID = "_id";
    public static final String TODO = "todo";
    public static final String STATE = "state";
    public static final String COLOR = "color";

    // fields for Marten:
    public static final String SYNC_SOURCE = "sync_source";
    public static final String SYNC_VERSION = "sync_version";
    public static final String UID = "uid";
    public static final String ORIGINAL_INSTANCE = "original_instance";
    public static final String ORIGINAL_INSTANCE_TIME = "original_instance_time";
    public static final String DIRTY = "dirty";
    public static final String DELETED = "deleted";


    private static final int TODOS = 1;
    private static final int TODO_ID = 2;

    private static final UriMatcher uriMatcher;
    static
    {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "todos", TODOS);
        uriMatcher.addURI(PROVIDER_NAME, "todos/#", TODO_ID);
    }

    private SQLiteDatabase tasksDB;
    private static final String DATABASE_NAME = "TimeMachineDataBase";
    private static final String DATABASE_TABLE = "Todos";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE +
                    " (_id integer primary key autoincrement, "+
                    TODO +" text not null, "+
                    STATE+" int not null, "+
                    COLOR+" int not null, "+
                    // Fields for Marten:
                    SYNC_SOURCE +" text, "+
                    SYNC_VERSION +" text, "+
                    UID +" text, " +
                    ORIGINAL_INSTANCE +" int, "+
                    ORIGINAL_INSTANCE_TIME + " long, "+
                    DIRTY +" int, "+
                    DELETED +" int);";

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DATABASE_CREATE);
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
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        tasksDB = dbHelper.getWritableDatabase();
        return (tasksDB != null);
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
        sqlBuilder.setTables(DATABASE_TABLE);

        if (uriMatcher.match(uri) == TODO_ID)
            //---if getting a particular book---
            sqlBuilder.appendWhere(
                    _ID + " = " + uri.getPathSegments().get(1));

        if (sortOrder == null || sortOrder == "")
            sortOrder = TODO;

        Cursor c = sqlBuilder.query(
                tasksDB,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        //---register to watch a content URI for changes---
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
    //---add a new book---
        long rowID = tasksDB.insert(
                DATABASE_TABLE, "", contentValues);

        //---if added successfully---
        if (rowID>0)
        {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to insert row into " + uri);    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int count=0;
        switch (uriMatcher.match(uri)){
            case TODOS:
                count = tasksDB.delete(
                        DATABASE_TABLE,
                        s,
                        strings);
                break;
            case TODO_ID:
                String id = uri.getPathSegments().get(1);
                count = tasksDB.delete(
                        DATABASE_TABLE,
                        _ID + " = " + id +
                                (!TextUtils.isEmpty(s) ? " AND (" +
                                        s + ')' : ""),
                        strings);
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
        switch (uriMatcher.match(uri)){
            case TODOS:
                count = tasksDB.update(
                        DATABASE_TABLE,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            case TODO_ID:
                count = tasksDB.update(
                        DATABASE_TABLE,
                        contentValues,
                        _ID + " = " + uri.getPathSegments().get(1) +
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
