package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;

import com.activeandroid.util.Log;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class SearchTweetsFragment extends TweetsListFragment {

    TwitterClient client;
    String query;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        query = getArguments().getString("query", "");

        populateTimeline();


    }

    public static SearchTweetsFragment newInstance(String query){
        SearchTweetsFragment searchTweetsFragment = new SearchTweetsFragment();
        Bundle args = new Bundle();
        args.putString("query", query);
        searchTweetsFragment.setArguments(args);
        return searchTweetsFragment;
    }

    public void populateTimeline() {
        client.doMySearch(query, new JsonHttpResponseHandler() {
            // SUCCESS

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                Log.d("DEBUG", json.toString());
                // DESERIALIZE JSON
                // CREATE MODELS AND ADD THEM TO THE ADAPTER
                // LOAD THE MODEL DATA INTO LISTVIEW
                try {
                    addAll(Tweet.fromJSONArray(json.getJSONArray("statuses")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }

        });
    }

}