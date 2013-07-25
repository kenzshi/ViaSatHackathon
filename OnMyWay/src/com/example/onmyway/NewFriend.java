package com.example.onmyway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class NewFriend extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_new_friend);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_friend, menu);
		return true;
	}

	public void submitFriend(View view) {
		EditText editText = (EditText) findViewById(R.id.friend_name);
    	String name = editText.getText().toString();
    	
    	Runnable addFriendThread = new AddFriend(this, name);
    	Thread add_friends = new Thread(addFriendThread);
		
		add_friends.start();
		
		try{add_friends.join();}catch(InterruptedException ie){}
		
		finish();
		Intent intent= new Intent(this, LoggedIn.class);
		MainActivity.count = 0;
		startActivity(intent);
		
	}
	
}
