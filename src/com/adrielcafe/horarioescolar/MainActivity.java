package com.adrielcafe.horarioescolar;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adrielcafe.horarioescolar.list.Header;
import com.adrielcafe.horarioescolar.list.Item;
import com.adrielcafe.horarioescolar.list.ListAdapter;
import com.adrielcafe.horarioescolar.list.ListItem;

public class MainActivity extends ListActivity {
	private static final String[] DAYS_WEEK = new String[]{"Segunda-Feira", "Terça-Feira", "Quarta-Feira", "Quinta-Feira", "Sexta-Feira", "Sábado"};
	
	private TextView schoolName;
	private TextView studentName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4A5A6B")));
		getActionBar().setIcon(R.drawable.logo);
		setTitle(Html.fromHtml("<b>" + getString(R.string.app_name) + "</b>"));
		setContentView(R.layout.activity_main);
		
		schoolName = (TextView) findViewById(R.id.schoolName);
		studentName = (TextView) findViewById(R.id.studentName);
		
		String url = getPreferences(Context.MODE_PRIVATE).getString(Util.PREF_URL, null);
		updateUserData(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.action_refresh:
				actionRefresh();
				break;
			case R.id.action_settings:
				actionSettings();
				break;
			case R.id.action_about:
				actionAbout();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void actionRefresh(){
		String url = getPreferences(Context.MODE_PRIVATE).getString(Util.PREF_URL, null);
		updateUserData(url); 
	}

	private void actionSettings(){
		String url = getPreferences(Context.MODE_PRIVATE).getString(Util.PREF_URL, null);
		View view = getLayoutInflater().inflate(R.layout.fragment_settings, null);
		final TextView urlView = (TextView) view.findViewById(R.id.url);
		
		if(url != null)
			urlView.setText(url); 
		
		new AlertDialog.Builder(this)
			.setTitle(R.string.settings)
			.setView(view)
			.setNegativeButton(R.string.cancel, null)
			.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					updateUserData(urlView.getText().toString());
				}
			})
			.show();
	}

	private void actionAbout(){
		LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		TextView tv = new TextView(this);
		tv.setText(Html.fromHtml(Util.ABOUT_MESSAGE));
		tv.setPadding(10, 10, 10, 10);
		tv.setLayoutParams(layout);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setTextAppearance(this, android.R.style.TextAppearance_Medium);

		new AlertDialog.Builder(this)
			.setTitle(R.string.about)
			.setView(tv)
			.show();
	}
	
	private void updateUserData(final String url){
		if(url == null)
			updateList(null);
		else
			new AsyncTask<Void, Void, String>(){
				@Override
				protected String doInBackground(Void... args) {
					try {
						return IOUtils.toString(new URL(url));
					} catch(Exception e){ 
						return null;
					}
				}
				
				@Override
				protected void onPostExecute(String json) {
					if(json == null){
						String userData = getPreferences(Context.MODE_PRIVATE).getString(Util.PREF_USERDATA, null);
						updateList(Util.gson.fromJson(userData, UserData.class));
					} else {
						UserData userData = Util.gson.fromJson(json, UserData.class);
						getPreferences(Context.MODE_PRIVATE)
							.edit()
							.putString(Util.PREF_URL, url)
							.putString(Util.PREF_USERDATA, json)
							.commit();
						updateList(userData);
						Toast.makeText(MainActivity.this, R.string.data_updated, Toast.LENGTH_SHORT).show();
					}
				};
			}.execute();
	}
	
	private void updateList(UserData userData){
		List<Item> items = new ArrayList<Item>();
		
		if(userData == null){
			items.add(new ListItem(getString(R.string.wrong_config), ""));
		} else {
			schoolName.setText(userData.schoolName);
			studentName.setText(userData.studentName);
			
			for(String dayWeek : DAYS_WEEK){
		        items.add(new Header(dayWeek));
		        
				if(dayWeek.equals("Segunda-Feira")){
			        for(String[] i : userData.monday)
			            items.add(new ListItem(i[0], i[1]));
				} else if(dayWeek.equals("Terça-Feira")){
					for(String[] i : userData.tuesday)
			            items.add(new ListItem(i[0], i[1]));
				} else if(dayWeek.equals("Quarta-Feira")){
					for(String[] i : userData.wednesday)
			            items.add(new ListItem(i[0], i[1]));
				} else if(dayWeek.equals("Quinta-Feira")){
					for(String[] i : userData.thursday)
			            items.add(new ListItem(i[0], i[1]));
				} else if(dayWeek.equals("Sexta-Feira")){
					for(String[] i : userData.friday)
			            items.add(new ListItem(i[0], i[1]));
				} else if(dayWeek.equals("Sábado")){
					for(String[] i : userData.saturday)
			            items.add(new ListItem(i[0], i[1]));
				}
			}
		}
		
        ListAdapter adapter = new ListAdapter(this, items);
        setListAdapter(adapter);
	}
}