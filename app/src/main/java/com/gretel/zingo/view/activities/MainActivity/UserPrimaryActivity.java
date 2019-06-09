package com.gretel.zingo.view.activities.MainActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;

import com.google.common.collect.HashBiMap;
import com.gretel.zingo.view.activities.ReportActivity.ReportActivity;
import com.gretel.zingo.view.fragments.ByAuthorityFragment;
import com.gretel.zingo.view.fragments.HomeFragment;
import com.gretel.zingo.view.fragments.HotFragment;
import com.gretel.zingo.view.fragments.LatestFragment;
import com.gretel.zingo.R;

import static com.gretel.zingo.view.activities.MainActivity.MainActivity.FragmentType.AUTHORITIES;
import static com.gretel.zingo.view.activities.MainActivity.MainActivity.FragmentType.CONTACT_US;
import static com.gretel.zingo.view.activities.MainActivity.MainActivity.FragmentType.HOME;
import static com.gretel.zingo.view.activities.MainActivity.MainActivity.FragmentType.HOT;
import static com.gretel.zingo.view.activities.MainActivity.MainActivity.FragmentType.LATEST;
import static com.gretel.zingo.view.activities.MainActivity.MainActivity.FragmentType.MY_REPORTS;
import static com.gretel.zingo.view.activities.MainActivity.MainActivity.FragmentType.USER;

/**
 * This is the main activity of the app and hosts all the fragments related to BottomNavigationBar
 * and Navigation Drawer.
 * @author Amik Mandal
 */
public class UserPrimaryActivity extends PrimaryActivity  {

    @Override
    protected void createFragmentTypeMap() {
        myFragmentIDs = HashBiMap.create();
        myFragmentIDs.put(HOME,0);
        myFragmentIDs.put(LATEST,1);
        myFragmentIDs.put(HOT,2);
        myFragmentIDs.put(AUTHORITIES,3);
    }

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_main);
        FloatingActionButton newReport = findViewById(R.id.main_button);
        newReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initializeFragment() {
        System.out.println("order check ---> UserPrimaryActivity-initializeFragment");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).addToBackStack(HOME.toString()).commit();
        myPrevFragment = new HomeFragment();
        myPrevFragmentID = myFragmentIDs.get(HOME);
        myCurrentFragmentID = myPrevFragmentID;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){

        switch(item.getItemId()){
            case R.id.nav_user:
                openDrawerFragment(item,USER);
                break;
            case R.id.nav_contact_us:
                openDrawerFragment(item,CONTACT_US);
                break;
            case R.id.nav_my_reports:
                openDrawerFragment(item,MY_REPORTS);
                break;
            case R.id.navigation_home:
                openNavFragment(new HomeFragment(), myFragmentIDs.get(HOME));
                createFragment(R.id.fragment_container,myFragmentIDs.get(HOME),new HomeFragment(),HOME.toString());
                break;
            case R.id.navigation_latest:
                openNavFragment(new LatestFragment(), myFragmentIDs.get(LATEST));
                createFragment(R.id.fragment_container,myFragmentIDs.get(LATEST),new LatestFragment(),LATEST.toString());
                break;
            case R.id.navigation_hot:
                openNavFragment(new HotFragment(), myFragmentIDs.get(HOT));
                createFragment(R.id.fragment_container,myFragmentIDs.get(HOT),new HotFragment(),HOT.toString());
                break;
            case R.id.navigation_authorities:
                openNavFragment(new ByAuthorityFragment(), myFragmentIDs.get(AUTHORITIES));
                createFragment(R.id.fragment_container,myFragmentIDs.get(AUTHORITIES),new ByAuthorityFragment(),AUTHORITIES.toString());
                break;
            case R.id.nav_review:
                break;
            case R.id.nav_logout:
                logOutUser();
                break;

        }

        return super.onNavigationItemSelected(item);

    }

    @Override
    protected Intent getSecondaryActivity() {
        return new Intent(getApplicationContext(),UserSecondaryActivity.class);
    }

}