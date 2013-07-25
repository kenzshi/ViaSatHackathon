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


public class UpdateCoordinates implements Runnable {
	
	Activity context;
	String userName;
	
	UpdateCoordinates(Activity cnt) {
		context = cnt;
	}
	
	public void updateCoordinates() {

		String result = "";
		InputStream is = null;

		Log.e("GetCoordinates", "BEGIN");
		
		GPSTracker tracker = new GPSTracker(context);
		Utilities.latitude = tracker.getLatitude();
		Utilities.longitude = tracker.getLongitude();
		
		//http post
		try {
			HttpClient httpclient = new DefaultHttpClient();
			
			HttpPost httppost = new HttpPost("http://www.justinalanbass.com/php/update_coordinates.php?" +
					"user_name="+ Utilities.userName + "&latitude=" + Double.toString(Utilities.latitude) +  "&longitude=" + Double.toString(Utilities.longitude));
			
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
				Log.e("GetCoordinates", message);
			} else {
				Log.e("GetCoordinates","not success");	
			}
			
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
	}
		

	
	public void run() {
		updateCoordinates();
	}
	
}
