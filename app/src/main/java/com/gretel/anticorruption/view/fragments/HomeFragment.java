package com.gretel.anticorruption.view.fragments;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gretel.anticorruption.R;
import com.gretel.anticorruption.view.activities.ReportActivity.ReportActivity;

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
                Intent intent = new Intent(getActivity().getApplicationContext(), ReportActivity.class);
                startActivity(intent);
            }
        };
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
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
