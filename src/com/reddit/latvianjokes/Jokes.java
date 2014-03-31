package com.reddit.latvianjokes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Jokes extends Activity {

	public void seeJokes(View view)
	{
		Intent intent = new Intent(this, com.reddit.latvianjokes.Jokes.class);
		startActivity(intent);
		
	}
	
	public void goBack(View view)
	{
		Intent intent = new Intent(this, com.reddit.latvianjokes.HomeActivity.class);
		startActivity(intent);
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_jokes);

	}


}