package com.adrielcafe.horarioescolar.list;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.adrielcafe.horarioescolar.R;
import com.adrielcafe.horarioescolar.list.ListAdapter.RowType;

public class ListItem implements Item {
	private final String name;
	private final String hour;

    public ListItem(String name, String hour) {
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
            view = (View) inflater.inflate(R.layout.list_item, null);
        else
            view = convertView;

        TextView nameView = (TextView) view.findViewById(R.id.name);
        TextView hourView = (TextView) view.findViewById(R.id.hour);
        nameView.setText(name);
        hourView.setText(hour);

        return view;
	}

}