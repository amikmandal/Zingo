package com.gretel.zingo.view.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gretel.zingo.R;
import com.gretel.zingo.view.fragments.MatchAttendeesFragment;
import com.gretel.zingo.view.fragments.MatchEventsFragment;
import com.gretel.zingo.view.fragments.MatchHostsFragment;

public class MatchPageAdapter extends FragmentPagerAdapter {

    // tab titles
    private String[] myTabTitles = new String[3];

    private int myNumOfTabs;

    public MatchPageAdapter(FragmentManager fragmentManager, int numOfTabs, Context context){
        super(fragmentManager);
        myNumOfTabs = numOfTabs;

        myTabTitles[0] = context.getResources().getString(R.string.tab_hosts);
        myTabTitles[1] = context.getResources().getString(R.string.tab_events);
        myTabTitles[2] = context.getResources().getString(R.string.tab_attendees);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MatchHostsFragment();
            case 1:
                return new MatchEventsFragment();
            case 2:
                return new MatchAttendeesFragment();
            default:
                return new MatchHostsFragment();
        }
    }

    @Override
    public int getCount() {
        return myNumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return myTabTitles[position];
    }
}
