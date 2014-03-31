package com.reddit.latvianjokes;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import java.util.Random;

public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	}
	
	public void seeJokes(View view)
	{
		Intent intent = new Intent(this, com.reddit.latvianjokes.Jokes.class);
		startActivity(intent);
		
	}
	
	public void seeAbout(View view)
	{
		Random randomNumber = new Random();
			int randomInt = randomNumber.nextInt(10);
			
			if(randomInt == 9){
				Intent intent = new Intent(this, com.reddit.latvianjokes.Politburo.class);
				startActivity(intent);
				
			}else{
				
				Intent intent = new Intent(this, com.reddit.latvianjokes.About.class);
				startActivity(intent);
			}
	}


}