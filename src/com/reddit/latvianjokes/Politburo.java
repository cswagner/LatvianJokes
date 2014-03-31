package com.reddit.latvianjokes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Politburo extends Activity {
	
	public void goMain(View view)
	{
		Intent intent = new Intent(this, com.reddit.latvianjokes.HomeActivity.class);
		startActivity(intent);
		
	}
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_politburo);
	}

}
