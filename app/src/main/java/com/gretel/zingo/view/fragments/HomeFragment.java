package com.gretel.zingo.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gretel.zingo.R;
import com.gretel.zingo.view.activities.MainActivity.MainActivity;

public class HomeFragment extends MainFragment {

    private DatabaseReference myReportDatabase;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myReportDatabase = FirebaseDatabase.getInstance().getReference("reports");
    }

    @Override
    protected View.OnClickListener getButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        };
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_scroll;
    }

    @Override
    public void onStart(){
        super.onStart();

        myReportDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

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
