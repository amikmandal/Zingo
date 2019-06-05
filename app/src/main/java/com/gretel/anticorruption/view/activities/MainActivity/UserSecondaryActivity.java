package com.gretel.anticorruption.view.activities.MainActivity;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import com.google.common.collect.HashBiMap;
import com.gretel.anticorruption.R;
import com.gretel.anticorruption.view.fragments.ContactUsFragment;
import com.gretel.anticorruption.view.fragments.MyReportsFragment;
import com.gretel.anticorruption.view.fragments.UserFragment;

import static com.gretel.anticorruption.view.activities.MainActivity.MainActivity.FragmentType.CONTACT_US;
import static com.gretel.anticorruption.view.activities.MainActivity.MainActivity.FragmentType.MY_REPORTS;
import static com.gretel.anticorruption.view.activities.MainActivity.MainActivity.FragmentType.USER;
import static com.gretel.anticorruption.view.fragments.UserFragment.FragmentMode.EDIT;

public class UserSecondaryActivity extends SecondaryActivity {

    @Override
    protected void createFragmentTypeMap() {
        myFragmentIDs = HashBiMap.create();
        myFragmentIDs.put(USER,0);
        myFragmentIDs.put(MY_REPORTS,1);
        myFragmentIDs.put(CONTACT_US,2);
    }

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_secondary_user);
    }

    @Override
    protected void initializeFragment() {
        System.out.println("order check ---> UserSecondary-initializeFragment");
        switch((FragmentType) getIntent().getSerializableExtra("openFragment")){
            case USER:
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(USER),new UserFragment(),USER.toString());
                break;
            case MY_REPORTS:
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(MY_REPORTS),new MyReportsFragment(),MY_REPORTS.toString());
                break;
            case CONTACT_US:
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(CONTACT_US),new ContactUsFragment(),CONTACT_US.toString());
                break;
        }
    }

    @Override
    protected void addToolBarButton() {
        if(myCurrentFragmentID==null)
        switch (myFragmentIDs.inverse().get(myCurrentFragmentID)){
            case USER:
                myToolbarButton.setBackgroundResource(R.drawable.ic_edit);
                myToolbarButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myEditButtonListener.onEditButtonSelect(myToolbarButton);
                    }
                });
                break;
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_user:
                prepareForNewFragment(item);
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(USER),new UserFragment(),USER.toString());
                break;
            case R.id.nav_my_reports:
                prepareForNewFragment(item);
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(MY_REPORTS),new MyReportsFragment(),MY_REPORTS.toString());
                break;
            case R.id.nav_contact_us:
                prepareForNewFragment(item);
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(CONTACT_US),new ContactUsFragment(),CONTACT_US.toString());
                break;
        }

        return super.onNavigationItemSelected(item);
    }

    protected void handleSpecializedBackPress() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.secondary_fragment_container);
        switch (myFragmentIDs.inverse().get(myCurrentFragmentID)){
            case USER:
                UserFragment userFragment = (UserFragment) fragment;
                if (userFragment != null && userFragment.changesMade() && userFragment.getCurrentMode() == EDIT) {
                    userFragment.handleCancelButton(fragment.getView());
                }
                break;
        }
    }

}