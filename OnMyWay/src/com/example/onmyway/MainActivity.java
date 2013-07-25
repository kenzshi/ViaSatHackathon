package com.example.onmyway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	public static int count = 0;
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

    public void loginUser(View view){
    	Intent intent = new Intent(this, LoggedIn.class);
    	EditText editText = (EditText) findViewById(R.id.login_name);
    	String message = editText.getText().toString();
    	Utilities.userName = message;
    	intent.putExtra(EXTRA_MESSAGE, message);
    	TextView waitMessage = (TextView) findViewById(R.id.progress);
    	waitMessage.setVisibility(View.VISIBLE);
    	startActivity(intent);
    }
}
