package com.gretel.anticorruption.view.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gretel.anticorruption.R;
import com.gretel.anticorruption.model.Agent.Authority;
import com.gretel.anticorruption.util.LocalStorage;
import com.gretel.anticorruption.view.activities.MainActivity.AuthorityPrimaryActivity;

import static com.gretel.anticorruption.view.activities.MainActivity.MainActivity.FragmentType.REQUEST_LIST;

public class DashboardFragment extends MainFragment {

    private Query myQuery;

    @Override
    protected View.OnClickListener getButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AuthorityPrimaryActivity)getActivity()). openNavFragment(new MyAuthorityFragment(), 1);
                ((AuthorityPrimaryActivity)getActivity()).createFragment(R.id.primary_fragment_container,1,new MyAuthorityFragment(),REQUEST_LIST.toString());
            }
        };
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LocalStorage localStorage = new LocalStorage(context);
        Authority a = localStorage.loadAuthority();
        DatabaseReference reportDatabase = FirebaseDatabase.getInstance().getReference("reports");
        myQuery = reportDatabase.orderByChild("authority").equalTo(a.getAuthorityName());
    }

    @Override
    public void onStart(){
        super.onStart();

        myQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long reportCount = dataSnapshot.getChildrenCount();
                if(reportCount>Integer.MAX_VALUE)
                    reportCount=Integer.MAX_VALUE;
                startCountAnimation((int) reportCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
}
