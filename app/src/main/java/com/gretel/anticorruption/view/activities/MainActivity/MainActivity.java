package com.gretel.anticorruption.view.activities.MainActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.common.collect.HashBiMap;
import com.gretel.anticorruption.R;
import com.gretel.anticorruption.view.activities.FormActivity.AuthorityFormActivity;
import com.gretel.anticorruption.view.activities.LoginActivity.LoginActivity;
import com.gretel.anticorruption.util.LocalStorage;
import com.gretel.anticorruption.util.UserLoader;

public abstract class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public enum FragmentType{
        HOME,LATEST,HOT,AUTHORITIES,USER,MY_REPORTS,FRIEND_REQUESTS,CONTACT_US,DASHBOARD,REQUEST_LIST,CONTACT_CUSTOMER,REPAIRER,
    }

    protected enum TransitionType {
        SLIDE_LEFT,SLIDE_RIGHT,SLIDE_BOTTOM
    }

    protected Integer myCurrentFragmentID;
    protected Integer myPrevFragmentID;

    protected DrawerLayout myDrawer;
    protected NavigationView myDrawerNavigationView;
    protected Fragment myPrevFragment;
    protected Button myToolbarButton;
    protected TextView myNavUser;
    protected HashBiMap<FragmentType,Integer> myFragmentIDs;

    /**
     * This method initializes the fragment to the activity
     */
    protected abstract void initializeFragment();

    /**
     * This method adds the button to the toolbar and specifies its actions
     */
    protected abstract void addToolBarButton();

    /**
     * This method determines the transition type between fragments involved in main activity
     * @param fragmentID specifies the id of the new fragment
     * @return the transition type
     */
    protected abstract TransitionType determineTransition(int fragmentID);

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch(item.getItemId()) {
            case R.id.nav_switch_to_user:
                intent = new Intent(getApplicationContext(),UserPrimaryActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_switch_to_authority:
                LocalStorage localStorage = new LocalStorage(getApplicationContext());
                if(!localStorage.checkIfAuthorityPresent()){
                    intent = new Intent(getApplicationContext(), AuthorityFormActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), AuthorityPrimaryActivity.class);
                }
                startActivity(intent);
                break;
            case R.id.nav_review:
                break;
            case R.id.nav_logout:
                logOutUser();
                break;
        }

        myDrawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * This method helps in adding the basic UI elements that is common to all inherited activities
     * such as but not limited to Toolbar, Navigation Drawer, etc.
     */
    protected void addUIElements() {

        System.out.println("order check ---> MainActivity-addUIElements");

        UserLoader userLoader = new UserLoader(this,getApplicationContext());
        userLoader.execute();

        //toolbar frontend
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        myToolbarButton = findViewById(R.id.menu_button);

        //drawer frontend
        myDrawer = findViewById(R.id.main_container_drawer);
        myDrawerNavigationView = findViewById(R.id.main_nav_view);
        View headerView =  myDrawerNavigationView.getHeaderView(0);
        myNavUser = headerView.findViewById(R.id.header_greetings);
        myDrawerNavigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,myDrawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        myDrawer.addDrawerListener(toggle);
        toggle.syncState();

        initializeFragment();

        addToolBarButton();

    }

    /**
     * This method creates a fragment
     * @param fragmentContainer specifies the part of screen the fragment must confine in.
     * @param fragment specifies the fragment to be displayed
     * @param fragmentName specifies the fragment name to be associated with in the stack
     */
    public void createFragment(int fragmentContainer, int fragmentID, Fragment fragment, String fragmentName){

        System.out.println("order check ---> MainActivity-createFragment");

        TransitionType transitionType = determineTransition(fragmentID);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
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
        fragmentTransaction.replace(fragmentContainer,fragment).addToBackStack(fragmentName).commit();

        myCurrentFragmentID = fragmentID;

    }

    /**
     * This method is used to delete user data from shared preference and log out a user.
     */
    protected void logOutUser() {
        LocalStorage localStorage = new LocalStorage(getApplicationContext());

        String loginType = localStorage.loadString("loginType");
        localStorage.removeUser();
        localStorage.removeAuthority();

        if(loginType.equals("facebook")){
            LoginManager.getInstance().logOut();
        }

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Getter method to send textview for displaying username in Navigation Drawer upon completion of Local Storage.
     * @return
     */
    public TextView getNavHeaderTextView(){
        return myNavUser;
    }

    /**
     * Setter method to set the appropriate layout file
     */
    protected abstract void setLayout();

    /**
     * Creates the fragment map to deal with different fragments
     */
    protected abstract void createFragmentTypeMap();
}
