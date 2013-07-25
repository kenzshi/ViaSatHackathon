package com.example.onmyway;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;

public class NetworkThreadGMaps implements Runnable {
	
	Activity act;
	String un_origin;
	String un_destination;
	double lat_origin, long_origin, lat_destination, long_destination;
	String eta;
	
	public void get_eta() {
		String temp = "";
		
		String result = "";
		InputStream is = null;

		Log.e("abc", "BEGIN");
		
		//http post
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://justinalanbass.com/php/get_coordinates.php?user_name="+un_origin);
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
			//Log.e("abc",result);
		} catch(Exception e11){
			Log.e("log_tag", "Error converting result "+e11.toString());
		}
		 
		//parse json data
		try{
			JSONObject obj = new JSONObject(result);
			
			if( obj.getInt("success") == 1 ) {
				lat_origin = obj.getDouble("latitude");
				long_origin = obj.getDouble("longitude");
				
				Log.e("abc", "Z" + lat_origin + " " + long_origin);
				
			} else {
				Log.e("abc","not success");	
			}
			
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
		//http post
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://justinalanbass.com/php/get_coordinates.php?user_name="+un_destination);
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
			//Log.e("abc",result);
		} catch(Exception e11){
			Log.e("log_tag", "Error converting result "+e11.toString());
		}
		 
		//parse json data
		try{
			JSONObject obj = new JSONObject(result);
			
			if( obj.getInt("success") == 1 ) {
				lat_destination = obj.getDouble("latitude");
				long_destination = obj.getDouble("longitude");
				
				Log.e("abc", "Z" + lat_destination + " " + long_destination);
				
			} else {
				Log.e("abc","not success");	
			}
			
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
		//http post
		try {
			HttpClient httpclient = new DefaultHttpClient();
			String command1 = "http://maps.googleapis.com/maps/api/distancematrix/json?origins="+long_origin+","+lat_origin+"&destinations="+long_destination+","+lat_destination+"&sensor=true";
			HttpPost httppost = new HttpPost(command1);
			Log.e("abc", command1);
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
			//Log.e("abc",result);
		} catch(Exception e11){
			Log.e("log_tag", "Error converting result "+e11.toString());
		}
		 
		//parse json data
		try{
			JSONObject obj = new JSONObject(result);
			
			JSONArray rows = obj.getJSONArray("rows");
			JSONObject rows1 = rows.getJSONObject(0);
			
			JSONArray elements = rows1.getJSONArray("elements");
			JSONObject elements1 = elements.getJSONObject(0);
			
			JSONObject duration = elements1.getJSONObject("duration");
			
			eta = duration.getString("text");
			Log.e("abc", "ETA " + eta);
			
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
		act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //btn.setText("#" + i);
            	LinearLayout lView = new LinearLayout(act);
            	TextView textView = new TextView(act);
        		textView.setTextSize(40);
        	    textView.setText(eta);
        	    lView.addView(textView);
        	    act.setContentView(lView);
            }
        });
		
		return;
	}
		
	NetworkThreadGMaps(Activity aact, String uno, String und) {
		act = aact;
		un_origin = uno;
		un_destination = und;
	}
	
	public void run() {
		get_eta();
	}
	
}
