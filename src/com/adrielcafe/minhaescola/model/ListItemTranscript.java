package com.adrielcafe.minhaescola.model;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.adrielcafe.minhaescola.ListAdapter.RowType;
import com.adrielcafe.minhaescola.R;

public class ListItemTranscript implements ListItem {
	private final String name;
	private final String note;

    public ListItemTranscript(String name, String note) {
        this.name = name;
        this.note = note;
    }
    
	@Override
	public int getViewType() {
		return RowType.LIST_ITEM.ordinal();
	}

	@Override
	public View getView(LayoutInflater inflater, View convertView) {
		View view;
        if (convertView == null)
            view = (View) inflater.inflate(R.layout.list_item_transcript, null);
        else
            view = convertView;

        TextView nameView = (TextView) view.findViewById(R.id.name);
        TextView noteView = (TextView) view.findViewById(R.id.note);
        nameView.setText(name);
        noteView.setText(note);

        return view;
	}

}