package com.adrielcafe.minhaescola.model;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.adrielcafe.minhaescola.ListAdapter.RowType;
import com.adrielcafe.minhaescola.R;

public class ListItemSchedule implements ListItem {
	private final String name;
	private final String hour;

    public ListItemSchedule(String name, String hour) {
        this.name = name;
        this.hour = hour;
    }
    
	@Override
	public int getViewType() {
		return RowType.LIST_ITEM.ordinal();
	}

	@Override
	public View getView(LayoutInflater inflater, View convertView) {
		View view;
        if (convertView == null)
            view = (View) inflater.inflate(R.layout.list_item_schedule, null);
        else
            view = convertView;

        TextView nameView = (TextView) view.findViewById(R.id.name);
        TextView hourView = (TextView) view.findViewById(R.id.hour);
        nameView.setText(name);
        hourView.setText(hour);

        return view;
	}

}