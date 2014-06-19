package com.adrielcafe.minhaescola.model;

import android.view.LayoutInflater;
import android.view.View;

public interface ListItem {
	public int getViewType();
    public View getView(LayoutInflater inflater, View convertView);
}