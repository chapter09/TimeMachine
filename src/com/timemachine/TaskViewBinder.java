//package com.timemachine;
//
///**
// * Created with IntelliJ IDEA.
// * User: wanghao
// * Date: 12-12-23
// * Time: 下午10:08
// */
//
//import android.database.Cursor;
//import android.graphics.Paint;
//import android.view.View;
//import android.widget.CheckBox;
//import android.widget.SimpleCursorAdapter;
//import android.widget.TextView;
//
///// For special values, which are supposed to go to non-text fields we have this special ViewBinder
//class TaskViewBinder implements SimpleCursorAdapter.ViewBinder
//{
//    public boolean setViewValue(View view, Cursor cursor, int columnIndex)
//    {
//        int viewId = view.getId();
//
//        if(viewId == R.id.checkBox)
//        {
//            CheckBox cb = (CheckBox) view;
//
//            if(cursor.getInt(cursor.getColumnIndexOrThrow(Tasks.AUTHORITY)) == 1)
//            {
//                // Check CheckBox
//                cb.setChecked(true);
//                // Add a Striketrough
//                cb.setPaintFlags(cb.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//            }
//            else
//            {
//                // Uncheck CheckBox
//                cb.setChecked(false);
//                // Remove Striketrough
//                cb.setPaintFlags( cb.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
//            }
//
//            cb.setText(cursor.getString(cursor.getColumnIndexOrThrow(Tasks.AUTHORITY)));
//
//            return true;
//        }
//        else if(viewId == R.id.colorBar)
//        {
//            int color = cursor.getInt(cursor.getColumnIndexOrThrow(Tasks.AUTHORITY));
//
//            TextView colorBar = (TextView)view;
//            colorBar.setBackgroundColor(color);
//
//            return true;
//        }
//
//        return false;
//    }
//}
