package com.gretel.anticorruption.view.activities.MainActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;

import com.gretel.anticorruption.R;
import com.gretel.anticorruption.util.EditButtonListener;

import static com.gretel.anticorruption.view.activities.MainActivity.MainActivity.TransitionType.SLIDE_BOTTOM;

abstract public class SecondaryActivity extends MainActivity{

    protected EditButtonListener myEditButtonListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("order check ---> SecondaryActivity-onCreate");
        createFragmentTypeMap();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setLayout();
        addUIElements();
    }

    protected void prepareForNewFragment(MenuItem item) {
        item.setChecked(true);
        for (Fragment fragment:getSupportFragmentManager().getFragments()) {
            if (fragment!=null) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }
        //hideKeyboard();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        System.out.println("order check ---> SecondaryActivity-onAttachFragment");
        if(fragment instanceof EditButtonListener){
            myEditButtonListener = (EditButtonListener) fragment;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myEditButtonListener = null;
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if(myDrawer.isDrawerOpen(GravityCompat.START)){
            myDrawer.closeDrawer(GravityCompat.START);
        } else if (count == 0) {
            super.onBackPressed();
        } else {
            //uncheck selected item from navigation drawer
            int size = myDrawerNavigationView.getMenu().size();
            for (int i = 0; i < size; i++) {
                myDrawerNavigationView.getMenu().getItem(i).setChecked(false);
            }
            handleSpecializedBackPress();
            //remove all fragments
            for (Fragment fragment:getSupportFragmentManager().getFragments()) {
                if (fragment!=null) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
            finish();
            //UserSecondaryActivity.this.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            this.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }
    }

    @Override
    protected TransitionType determineTransition(int fragmentID) {
        return SLIDE_BOTTOM;
    }

    protected abstract void handleSpecializedBackPress();
}
