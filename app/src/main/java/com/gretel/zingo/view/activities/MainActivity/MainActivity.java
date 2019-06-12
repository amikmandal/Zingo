package com.gretel.zingo.view.activities.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.common.collect.HashBiMap;
import com.gretel.zingo.R;
import com.gretel.zingo.view.fragments.EventFragment;
import com.gretel.zingo.view.fragments.InfoFragment;
import com.gretel.zingo.view.fragments.MatchFragment;
import com.gretel.zingo.view.fragments.ProfileFragment;

import java.util.HashMap;

import static com.gretel.zingo.view.activities.MainActivity.MainActivity.FragmentType.EVENTS;
import static com.gretel.zingo.view.activities.MainActivity.MainActivity.FragmentType.INFO;
import static com.gretel.zingo.view.activities.MainActivity.MainActivity.FragmentType.MATCHES;
import static com.gretel.zingo.view.activities.MainActivity.MainActivity.FragmentType.OTHERS;
import static com.gretel.zingo.view.activities.MainActivity.MainActivity.FragmentType.PROFILE;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected enum FragmentType{
        INFO,EVENTS,MATCHES,PROFILE,OTHERS
    }

    protected enum TransitionType {
        SLIDE_LEFT,SLIDE_RIGHT,SLIDE_BOTTOM
    }

    private Integer myPreviousFragmentID;

    protected HashMap<FragmentType,Integer> myFragmentIDs = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createFragmentMap();
        initializeActivity();

    }

    private void initializeActivity() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,new EventFragment()).addToBackStack(new EventFragment().toString()).commit();
        myPreviousFragmentID = myFragmentIDs.get(EVENTS);

        BottomNavigationView bottomNavigationView = findViewById(R.id.main_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_events);
    }

    private void createFragmentMap() {
        myFragmentIDs.put(INFO,0);
        myFragmentIDs.put(EVENTS,1);
        myFragmentIDs.put(MATCHES,2);
        myFragmentIDs.put(PROFILE,3);
        myFragmentIDs.put(OTHERS,4);
    }

    private TransitionType determineTransition(int newFragmentID) {

        if(newFragmentID>myPreviousFragmentID){
            return TransitionType.SLIDE_RIGHT;
        } else {
            return TransitionType.SLIDE_LEFT;
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){

        switch(item.getItemId()){
            case R.id.navigation_info:
                openFragment(new InfoFragment(),myFragmentIDs.get(INFO));
                break;
            case R.id.navigation_events:
                openFragment(new EventFragment(),myFragmentIDs.get(EVENTS));
                break;
            case R.id.navigation_matches:
                openFragment(new MatchFragment(),myFragmentIDs.get(MATCHES));
                break;
            case R.id.navigation_profile:
                openFragment(new ProfileFragment(),myFragmentIDs.get(PROFILE));
                break;
        }

        return true;
    }

    private void openFragment(Fragment fragment, int newFragmentID) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        TransitionType transitionType = determineTransition(newFragmentID);
        setTransition(transitionType,fragmentTransaction);
        fragmentTransaction.replace(R.id.fragment_container,fragment).addToBackStack(fragment.toString()).commit();
        myPreviousFragmentID = newFragmentID;
    }

    private void setTransition(TransitionType transitionType, FragmentTransaction fragmentTransaction) {
        switch (transitionType){
            case SLIDE_RIGHT:
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case SLIDE_LEFT:
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case SLIDE_BOTTOM:
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_top,R.anim.slide_out_bottom,R.anim.slide_in_bottom,R.anim.slide_out_top);
                break;
        }
    }

        @Override
        public void onBackPressed(){

        if (myPreviousFragmentID<4) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            super.onBackPressed();
        }

    }
}