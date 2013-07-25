package com.example.onmyway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.onmyway.MESSAGE";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void sendMessage(View view) {
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	//EditText editText = (EditText) findViewById(R.id.edit_message);
    	//String message = editText.getText().toString();
    	
    	GPSTracker tracker = new GPSTracker(MainActivity.this);
    	double longitude = tracker.getLongitude();
    	double latitude = tracker.getLatitude();
    	
    	String message = String.format("%.3f", longitude) + "," + String.format("%.3f", latitude);
    	
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);
    }
    public void loginUser(View view){
    	Intent intent = new Intent(this, LoggedIn.class);
    	EditText editText = (EditText) findViewById(R.id.login_name);
    	String message = editText.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);
    }
}
