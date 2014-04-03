package com.reddit.latvianjokes;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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
		
		final TextView tv = (TextView) this.findViewById(R.id.joke_text);
		AsyncTask<Void, Void, DBObject> task = new AsyncTask<Void, Void, DBObject>() {
			@Override
			protected DBObject doInBackground(Void... arg0) {
				return Potato.getPotato();				
			}
						
			@Override
			protected void onPostExecute(DBObject joke) {
				tv.setText(joke.get("title").toString());
			}
		};
		task.execute();
	}


}