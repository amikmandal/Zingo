package com.gretel.zingo.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gretel.zingo.R;
import com.gretel.zingo.view.adapters.MatchPageAdapter;

public class MatchFragment extends Fragment {

    TabLayout myTabLayout;
    TabItem myHostsTab;
    TabItem myEventsTab;
    TabItem myAttendeesTab;
    ViewPager myViewPager;
    MatchPageAdapter myMatchPageAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match,container,false);

        myTabLayout = view.findViewById(R.id.match_tabs);
        myHostsTab = view.findViewById(R.id.tab_hosts);
        myEventsTab = view.findViewById(R.id.tab_events);
        myAttendeesTab = view.findViewById(R.id.tab_attendees);
        myViewPager = view.findViewById(R.id.match_view_pager);

        myTabLayout.setupWithViewPager(myViewPager);

        myMatchPageAdapter = new MatchPageAdapter(getChildFragmentManager(),myTabLayout.getTabCount(),getContext());
        myViewPager.setAdapter(myMatchPageAdapter);
        myViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(myTabLayout));

        return view;
    }
}
