package com.example.onmyway;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import android.app.Activity;


public class SetPickUp implements Runnable {
	
	Activity context;
	String userName;
	
	SetPickUp(Activity cnt) {
		context = cnt;
	}
	
	public void setPickUp() {

		String result = "";
		InputStream is = null;

		Log.e("SetPickUp", "BEGIN");

		
		//http post
		try {
			HttpClient httpclient = new DefaultHttpClient();
			
			HttpPost httppost = new HttpPost("http://www.justinalanbass.com/php/add_pickup.php?" +
					"user_name="+ Utilities.userName + "&pickup=" + Utilities.pickupUser);
			
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			
			is = entity.getContent();
			
		} catch(Exception e1){
			Log.e("log_tag", "Error in http connection "+e1.toString());
		}
	
		//convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
				sb.append(line + "\n");

			is.close(); 
			result=sb.toString();

		} catch(Exception e11){
			Log.e("log_tag", "Error converting result "+e11.toString());
		}
		 
		//parse json data
		try{
			JSONObject obj = new JSONObject(result);
			
			if( obj.getInt("success") == 1 ) {
				
				String message = obj.getString("message");
				Log.e("SetPickUp", message);
			} else {
				Log.e("SetPickUp","not success");	
			}
			
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
	}
		

	
	@Override
	public void run() {
		setPickUp();
	}
	
}
