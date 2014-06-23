package com.adrielcafe.minhaescola;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.adrielcafe.minhaescola.model.ListItem;
import com.adrielcafe.minhaescola.model.ListItemHeader;
import com.adrielcafe.minhaescola.model.ListItemSchedule;
import com.adrielcafe.minhaescola.model.ListItemTranscript;
import com.adrielcafe.minhaescola.model.UserData;
import com.hb.views.PinnedSectionListView;

public class MainActivity extends Activity {
	private TextView schoolName;
	private TextView studentName;
	private PinnedSectionListView schedule;
	private PinnedSectionListView transcript;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
		getActionBar().setIcon(R.drawable.logo);
		setTitle(Html.fromHtml("<b>" + getString(R.string.app_name) + "</b>"));        
		setContentView(R.layout.activity_main);
		
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
		tabHost.setup();
		TabSpec spec1 = tabHost.newTabSpec("tabSchedule");
		spec1.setContent(R.id.tabSchedule);
		spec1.setIndicator(getString(R.string.schedule));
		tabHost.addTab(spec1);
		TabSpec spec2 = tabHost.newTabSpec("tabTranscript");
		spec2.setContent(R.id.tabTranscript);
		spec2.setIndicator(getString(R.string.transcript));
		tabHost.addTab(spec2);

		schoolName = (TextView) findViewById(R.id.schoolName);
		studentName = (TextView) findViewById(R.id.studentName);
		schedule = (PinnedSectionListView) findViewById(R.id.schedule);
		transcript = (PinnedSectionListView) findViewById(R.id.transcript);
		
		String url = getPreferences(Context.MODE_PRIVATE).getString(Util.PREF_URL, null);
		updateUserData(url == null || url.isEmpty() ? getString(R.string.url_demo) : url);
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
		updateUserData(url == null || url.isEmpty() ? getString(R.string.url_demo) : url); 
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
						Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
					}
				};
			}.execute();
	}
	
	private void updateList(UserData userData){
		List<ListItem> itemsSchedule = new ArrayList<ListItem>();
		List<ListItem> itemsTranscript = new ArrayList<ListItem>();
		
		if(userData == null){
			itemsSchedule.add(new ListItemSchedule(getString(R.string.wrong_config), ""));
			itemsTranscript.add(new ListItemSchedule(getString(R.string.wrong_config), ""));
		} else {
			schoolName.setText(userData.nomeEscola);
			studentName.setText(userData.nomeEstudante);

			for(String day : userData.horarios.keySet()){
				itemsSchedule.add(new ListItemHeader(day));
				for(String[] item : userData.horarios.get(day))
					itemsSchedule.add(new ListItemSchedule(item[0], item[1]));
			}
			
			for(String semester : userData.historico.keySet()){
				itemsTranscript.add(new ListItemHeader(semester));
				for(String[] item : userData.historico.get(semester))
					itemsTranscript.add(new ListItemTranscript(item[0], item[1]));
			}
		}

        ListAdapter adapterSchedule = new ListAdapter(this, itemsSchedule);
        ListAdapter adapterTranscript = new ListAdapter(this, itemsTranscript);
        schedule.setAdapter(adapterSchedule);
        transcript.setAdapter(adapterTranscript);
	}
}