package com.codepath.apps.mysimpletweets.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeNewTweetActivity extends AppCompatActivity {

    TwitterClient client;
    private TweetsArrayAdapter aTweets;
    private String body;
    private long uid; // unique id for the tweet
    private User user; // store embedded user object
    private String createdAt;
    TextView tvCharacterCount;
    EditText etComposeNewTweet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_new_tweet);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.twitter_logo_white);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        etComposeNewTweet = (EditText) findViewById(R.id.etComposeNewTweet);
        tvCharacterCount = (TextView) findViewById(R.id.tvCharacterCount);
        tvCharacterCount.setText(String.valueOf(140));

        // Change character count as text is entered
//        EditText etValue = (EditText) findViewById(R.id.etComposeNewTweet);
        etComposeNewTweet.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right as the text is being changed (even supplies the range of text)
//                Toast.makeText(ComposeNewTweetActivity.this, "Text changed!!!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Fires right before text is changing
//                Toast.makeText(ComposeNewTweetActivity.this, "Text changed!!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void afterTextChanged(Editable text) {
                // Fires right after the text has changed
//                Toast.makeText(ComposeNewTweetActivity.this, "Text changed!", Toast.LENGTH_SHORT).show();
                int i = Integer.parseInt(tvCharacterCount.getText().toString());
                i = 140 - text.length();
                tvCharacterCount.setText(String.valueOf(i));
            }
        });


        client = TwitterApplication.getRestClient();

    }

    // Post new tweet to twitter
    public void onTweet(View view) {
//        etComposeNewTweet = (EditText) findViewById(R.id.etComposeNewTweet);


        final Intent data = new Intent();
        client.composeNewTweet(etComposeNewTweet.getText().toString(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                Tweet tweet = Tweet.fromJSON(json);
//                tweet.setUser(tweet.getUser());
//                tweet.setBody(etComposeNewTweet.getText().toString());
//                tweet.setCreatedAt(tweet.getCreatedAt());
                data.putExtra("compose_tweet", tweet);
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish(); // closes the activity, pass data to parent
            }

        });

        // Activity finished ok, return the data
//        setResult(RESULT_OK, data); // set result code and bundle data for response
//        finish(); // closes the activity, pass data to parent
    }

    // Trying to change the line color of edittext
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = getLayoutInflater().inflate(R.layout.activity_compose_new_tweet, container);
//        return view;
//    }
}
