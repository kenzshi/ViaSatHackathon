package com.example.onmyway;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LoggedIn extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.onmyway.MESSAGE";
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//setContentView(R.layout.activity_logged_in);
		// Show the Up button in the action bar.
		//setupActionBar();
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE); //login name
		
		setContentView(R.layout.activity_logged_in); //Set the text view as the activity layout
		ArrayList<Thread> threads = new ArrayList();
		Runnable getFriendsThreads = new GetFriends(this,message);
		Thread add_friends = new Thread(getFriendsThreads);
		threads.add(add_friends);
		add_friends.start();
		try{add_friends.join();}catch(InterruptedException ie){}

		ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Utilities.friendsList);
		ListView listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(adapter);
		MainActivity.count = MainActivity.count+1;
		if(MainActivity.count==1){
		Intent intentRefresh = new Intent(this, LoggedIn.class);
		startActivity(intentRefresh);}

	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logged_in, menu);
		return true;
	}
	
	public void getFriends(View view) {
		Intent intent = new Intent(this, LoggedIn.class);
		startActivity(intent);
		//setContentView(R.layout.activity_logged_in); //Set the text view as the activity layout
    	/*Intent intent = new Intent(this, DisplayMessageActivity.class);
    	//EditText editText = (EditText) findViewById(R.id.edit_message);
    	//String message = editText.getText().toString();
    	
    	GPSTracker tracker = new GPSTracker(LoggedIn.this);
    	double longitude = tracker.getLongitude();
    	double latitude = tracker.getLatitude();
    	
    	String message = String.format("%.3f", longitude) + "," + String.format("%.3f", latitude);
    	
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);*/
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
