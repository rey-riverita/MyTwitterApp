package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.R;

/**
 * Created by riverita on 6/30/16.
 */
public class PagerFragment extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_pager, parent, false);
        // Get the viewpager
        ViewPager vpPager = (ViewPager) v.findViewById(R.id.viewpager);
        // Set the viewpager adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getActivity().getSupportFragmentManager()));
        // Find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) v.findViewById(R.id.tabs);
        // Attach the tabstrip to the viewpager
        tabStrip.setViewPager(vpPager);

        return v;
    }

    // Return the order of the fragments in the view pager
    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = {"Home", "Mentions"};

        // Adapter gets the manager which it uses to insert or remove fragment from activity
        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // The order and creation of fragments within the pager
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeTimelineFragment();
            } else if (position == 1) {
                return new MentionsTimelineFragment();
            } else {
                return null;
            }
        }

        // Return the tab title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        // How many fragments there are to swipe between?
        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
