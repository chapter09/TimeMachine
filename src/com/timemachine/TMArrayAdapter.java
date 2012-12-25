package com.timemachine;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
/**
 * Created by IntelliJ IDEA.
 * User: haow
 * Date: 12-12-9
 * Time: 下午9:00
 * add R
 */
public class TMArrayAdapter extends ArrayAdapter<Model> {
	private final List<Model> list;
	private final Activity context;

	public TMArrayAdapter(Activity context, List<Model> list) {
		super(context, R.layout.task_entry, list);
		this.context = context;
		this.list = list;
	}
//
//	public TMArrayAdapter(Context context, String[] values) {
//		super(context, R.layout.task_entry, values);
//		this.context = context;
//		this.values = values;
//	}

	static class ViewHolder {
		protected TextView textView;
		protected CheckBox checkBox;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.task_entry, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) view.findViewById(R.id.sqlID);
			viewHolder.checkBox = (CheckBox) view.findViewById(R.id.checkBox);
			viewHolder.checkBox
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
													 boolean isChecked) {
							Model  element = (Model) viewHolder.checkBox
									.getTag();
							element.setSelected(buttonView.isChecked());

						}
					});
			view.setTag(viewHolder);
			viewHolder.checkBox.setTag(list.get(position));
		} else {
			view = convertView;
			((ViewHolder) view.getTag()).checkBox.setTag(list.get(position));
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.textView.setText(list.get(position).getName());
		holder.checkBox.setChecked(list.get(position).isSelected());
		return view;
	}
}

class Model {

	private String name;
	private boolean selected;

	public Model(String name) {
		this.name = name;
		selected = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}