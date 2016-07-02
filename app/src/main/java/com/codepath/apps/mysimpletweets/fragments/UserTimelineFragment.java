package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;

import com.activeandroid.util.Log;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

// Loads in the user stream
public class UserTimelineFragment extends TweetsListFragment {

    private TwitterClient client;
    String screenName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenName = getArguments().getString("screen_name", "");

        // Get the client
        client = TwitterApplication.getRestClient(); // singleton client - will be using the same client across all our activities
        populateTimeline();


    }

    // Creates a new fragment given an int and title
    // UserTimelineFragment.newInstance("billybob");
    public static UserTimelineFragment newInstance(String screen_name) {
        UserTimelineFragment userFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screen_name);
        userFragment.setArguments(args);
        return userFragment;
    }

    // Send an API request to get the timeline json
    // Fill the listview by creating the tweet objects from the json
    public void populateTimeline() {
        String screenName = getArguments().getString("screen_name");
        client.getUserTimeline(screenName, new JsonHttpResponseHandler() {
            // SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                // DESERIALIZE JSON
                // CREATE MODELS AND ADD THEM TO THE ADAPTER
                // LOAD THE MODEL DATA INTO LISTVIEW
                addAll(Tweet.fromJSONArray(json));
            }

            // FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }

        });

    }

}
