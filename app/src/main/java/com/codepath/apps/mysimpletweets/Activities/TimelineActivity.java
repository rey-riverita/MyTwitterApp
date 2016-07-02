package com.codepath.apps.mysimpletweets.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.fragments.PagerFragment;
import com.codepath.apps.mysimpletweets.fragments.SearchTweetsFragment;
import com.codepath.apps.mysimpletweets.fragments.TweetsListFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity implements TweetsListFragment.OnProgressListener {

    private final int REQUEST_CODE = 20;
    TwitterClient client;
    private TweetsArrayAdapter aTweets;
    private ArrayList<Tweet> tweets;
    private ListView lvTweets;
    private User user;
    MenuItem miActionProgressItem;
    SearchView searchView;
    String query;

    SearchTweetsFragment searchTweetsFragment;
    PagerFragment pagerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        getSupportActionBar().setLogo(R.drawable.twitter_logo_white);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        client = TwitterApplication.getRestClient();
        searchTweetsFragment = new SearchTweetsFragment();
        pagerFragment = new PagerFragment();

        FragmentManager fm = getSupportFragmentManager(); // Handles transactions
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.flTimelineContainer, pagerFragment); // Where do you want to put it, from where do you want to put it
        ft.commit();

//        lvTweets = (ListView) findViewById(R.id.lvTweets);
//        tweets = new ArrayList<>();
//        aTweets = new TweetsArrayAdapter(this, tweets);
//        lvTweets.setAdapter(aTweets);

        //user = new User();
        client = TwitterApplication.getRestClient();

        // Done in PagerFragment now
//        // Get the viewpager
//        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
//        // Set the viewpager adapter for the pager
//        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
//        // Find the sliding tabstrip
//        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
//        // Attach the tabstrip to the viewpager
//        tabStrip.setViewPager(vpPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        // Handles search icon menu item
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                FragmentManager fm = getSupportFragmentManager(); // Handles transactions
                FragmentTransaction ft = fm.beginTransaction();

                SearchTweetsFragment searchTweetsFragment = SearchTweetsFragment.newInstance(query);

//                Bundle args = new Bundle();
//                args.putString("query", query);
//                searchTweetsFragment.setArguments(args);

                ft.replace(R.id.flTimelineContainer, searchTweetsFragment); // Where do you want to put it, from where do you want to put it
                ft.commit();

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void onProfileView(MenuItem mi) {
        // Launch the profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

//    public void onProfileImageView(View view) {
//        Intent i = new Intent(this, ProfileActivity.class);
//        i.putExtra("screen_name", );
//        startActivity(i);
//    }

    public void onComposeNewTweet(MenuItem item) {
        Intent i = new Intent(this, ComposeNewTweetActivity.class);
        i.putExtra("new_tweet_body", "");
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        aTweets = (TweetsArrayAdapter) lvTweets.getAdapter();


        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Tweet composedTweet = (Tweet) data.getExtras().getSerializable("compose_tweet");
            tweets = new ArrayList<>();
            for (int i = 0; i < aTweets.getCount(); i++){
                tweets.add(aTweets.getItem(i));
            }
            tweets.add(0, composedTweet);
            aTweets = new TweetsArrayAdapter(this, tweets);
            lvTweets.setAdapter(aTweets);


            //aTweets.notifyDataSetChanged();
            //lvTweets.setSelection(0);
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        // Return to finish
        return super.onPrepareOptionsMenu(menu);
    }

    public void showProgressBar() {
        // Show progress item
        if(miActionProgressItem != null) {
            miActionProgressItem.setVisible(true);
        }
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }

}
