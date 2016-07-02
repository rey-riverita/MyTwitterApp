package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.activeandroid.util.Log;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
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
public class HomeTimelineFragment extends TweetsListFragment {

    private TwitterClient client;
    private SwipeRefreshLayout swipeContainer;
    private TweetsArrayAdapter aTweets;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // Get the client
        client = TwitterApplication.getRestClient(); // singleton client - will be using the same client across all our activities
        populateTimeline();

    }

    // Send an API request to get the timeline json
    // Fill the listview by creating the tweet objects from the json
    public void populateTimeline() {
        listener.showProgressBar();
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            // SUCCESS

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());
                // DESERIALIZE JSON
                // CREATE MODELS AND ADD THEM TO THE ADAPTER
                // LOAD THE MODEL DATA INTO LISTVIEW
                addAll(Tweet.fromJSONArray(json));
                listener.hideProgressBar();
            }

            // FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse+"");
            }

        });

    }

}
