package com.reddit.latvianjokes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mongodb.DBObject;

import cswagner.latvianjokes.Potato;


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
		
		//DBObject joke = Potato.getPotato();
		TextView tv = (TextView) this.findViewById(R.id.joke_text);
		tv.setText("hello world");
		//String title = joke.get("title").toString();
		//tv.setText(title);
	}


}