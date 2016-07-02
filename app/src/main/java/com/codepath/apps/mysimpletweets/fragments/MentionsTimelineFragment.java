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

/**
 * Created by riverita on 6/27/16.
 */
public class MentionsTimelineFragment extends TweetsListFragment {


    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the client
        client = TwitterApplication.getRestClient(); // singleton client - will be using the same client across all our activities
        populateTimeline();

    }

    @Override
    protected void refreshTweets() {
        populateTimeline(); // May need to change in usertimeline
    }

    // Send an API request to get the timeline json
    // Fill the listview by creating the tweet objects from the json
    public void populateTimeline() {
        listener.showProgressBar();
        client.getMentionsTimeline(new JsonHttpResponseHandler() {
            // SUCCESS

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());
                // DESERIALIZE JSON
                // CREATE MODELS AND ADD THEM TO THE ADAPTER
                // LOAD THE MODEL DATA INTO LISTVIEW
                addAll(Tweet.fromJSONArray(json));
                listener.hideProgressBar();
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false); // usertimeline
            }

            // FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse+"");
            }

        });

    }


}
