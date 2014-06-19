package com.adrielcafe.minhaescola.model;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.adrielcafe.minhaescola.ListAdapter.RowType;
import com.adrielcafe.minhaescola.R;

public class ListItemHeader implements ListItem {
	private final String title;

    public ListItemHeader(String title) {
        this.title = title;
    }
    
	@Override
	public int getViewType() {
		return RowType.HEADER_ITEM.ordinal();
	}

	@Override
	public View getView(LayoutInflater inflater, View convertView) {
		View view;
        if (convertView == null)
            view = (View) inflater.inflate(R.layout.header_item, null);
        else
            view = convertView;
        
        TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(title);

        return view;
	}

}