package com.timemachine;

import android.app.Activity;
import android.content.Intent;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class TodayActivity extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// no more this
		// setContentView(R.layout.list_fruit);
		ArrayAdapter<Model> adapter = new TMArrayAdapter(this,
				getModel());

		setListAdapter(adapter);

		ListView listView = getListView();
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// When clicked, show Run
		    Intent i = new Intent(TodayActivity.this, RunActivity.class);
            startActivity(i);
			}
		});

		listView.setOnItemLongClickListener(
			new OnItemLongClickListener() {
				public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
					/// Change in the Content Provider
//					TextView tv = (TextView) view.findViewById(R.id.sqlID);
					CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox);

					/// Call the Editor
					Intent i = new Intent(TodayActivity.this, TaskEditor.class);
//					i.putExtra("todo", cb.getText());
//					i.putExtra("id", tv.getText());
//					i.putExtra("state", "null");
//					startActivityForResult(i, EDIT_DELETE);

					return true; // if false is returned onItemClick() will do the job
				}
			}
		);

	}

	private List<Model> getModel() {
		List<Model> list = new ArrayList<Model>();
		list.add(get("学做切糕"));
		list.add(get("看牙医"));
		list.add(get("学车"));
		list.add(get("打羽毛球"));
		list.add(get("上软工课"));
		list.add(get("升级iOS"));
		list.add(get("买2斤切糕"));
		list.add(get("理发"));
		list.add(get("学吉他"));
		list.add(get("实验室研讨会"));
		list.add(get("交软工作业"));
		list.add(get("背50个单词"));
		// Initially select one of the items
		list.get(1).setSelected(true);
		return list;
	}


	private Model get(String s) {
		return new Model(s);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{

	}

	protected ListAdapter createAdapter() {
		String[] testValues = new String[]{
				"Test1",
				"Test2",
				"Test3"
		};

		// Create a simple array adapter (of type string) with the test values
		ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, testValues);

		return adapter;
	}

//
//	@Override
//	public void onClick(View view) {
//		switch (view.getId()) {
//			case R.id.today_run:
//				Intent i = new Intent(this, RunActivity.class);
//				startActivity(i);
//				break;
//		}
//	}
}
