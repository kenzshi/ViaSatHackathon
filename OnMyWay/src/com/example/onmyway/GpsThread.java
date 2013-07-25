package com.example.onmyway;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class GpsThread implements Runnable{

	Context context;
	
	public GpsThread(Context cxt) {
		context = cxt;
		
		new Thread(this).start();		
	}
	
	
	public void run() {
		while(1==1) {
			synchronized (this) {
				try {
					this.wait(30000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
			Log.e("abc","Toast");

		}
	}
}
