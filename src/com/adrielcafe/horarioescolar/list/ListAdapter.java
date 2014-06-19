package com.adrielcafe.horarioescolar.list;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.hb.views.PinnedSectionListView.PinnedSectionListAdapter;

public class ListAdapter extends ArrayAdapter<Item> implements PinnedSectionListAdapter {
	private LayoutInflater inflater;
	
	public enum RowType {
        HEADER_ITEM,
        LIST_ITEM
    }
	
	public ListAdapter(Context context, List<Item> objects) {
		super(context, 0, objects);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public boolean isItemViewTypePinned(int viewType) {
		return viewType == RowType.HEADER_ITEM.ordinal();
	}

	@Override
	public int getViewTypeCount() {
	    return RowType.values().length;
	}
	
	@Override
	public int getItemViewType(int position) {
	    return getItem(position).getViewType();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    return getItem(position).getView(inflater, convertView);
	}
}