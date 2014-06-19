package com.adrielcafe.horarioescolar.list;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.adrielcafe.horarioescolar.R;
import com.adrielcafe.horarioescolar.list.ListAdapter.RowType;

public class Header implements Item {
	private final String title;

    public Header(String title) {
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