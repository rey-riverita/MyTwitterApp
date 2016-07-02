package com.codepath.apps.mysimpletweets.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.squareup.picasso.Picasso;


// Where the user will sign in to twitter
// OAUTH activity flow happens here
public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		String imageUri = "http://imgur.com/nXIUCH4.jpg";
		ImageView ivBasicImage = (ImageView) findViewById(R.id.ivTwitterLogo);
		Picasso.with(this).load(imageUri).into(ivBasicImage);
	}


	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
		Intent i = new Intent(this, TimelineActivity.class);
		startActivity(i);
	}

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
	public void loginToRest(View view) {
		getClient().connect();
	}

}
