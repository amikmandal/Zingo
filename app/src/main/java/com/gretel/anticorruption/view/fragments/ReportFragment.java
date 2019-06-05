package com.gretel.anticorruption.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gretel.anticorruption.R;
import com.gretel.anticorruption.model.Report.Report;
import com.gretel.anticorruption.view.adapters.ReportAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class ReportFragment extends Fragment {

    private RecyclerView myRecyclerView;
    private ReportAdapter myAdapter;

    protected View myView;

    protected ValueEventListener myListener;
    protected DatabaseReference myReportDatabase;
    protected Query myQuery;
    protected List<Report> myReports;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        System.out.println("order check ---> ReportFragment-onCreateView");

        View view = inflater.inflate(getFragmentLayout(),container,false);

        myReports = new ArrayList<>();
        myReportDatabase = FirebaseDatabase.getInstance().getReference("reports");
        myListener = getListener();
        setQuery();

        myView = view;

        initRecyclerView(view);

        return view;
    }

    protected abstract void setQuery();

    protected abstract int getFragmentLayout();

    @Override
    public void onStart(){
        System.out.println("order check ---> ReportFragment-onStart");
        myReports.clear();
        super.onStart();
        myQuery.addListenerForSingleValueEvent(myListener);
    }

    protected abstract void setText(Long count);

    /**
     * This method initializes the Recycler View for the home fragment
     * @param v specifies the view to which the recycler view for the list will be attached
     */
    protected void initRecyclerView(View v)
    {
        //call RecyclerView
        myRecyclerView = v.findViewById(R.id.report_recycler_view);
        ReportAdapter adapter = new ReportAdapter(myReports,getActivity().getApplicationContext());
        myRecyclerView.setAdapter(adapter);

        //check this
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }

    private ValueEventListener getListener(){
        return new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                setText(dataSnapshot.getChildrenCount());

                for(DataSnapshot d: dataSnapshot.getChildren()) {
                    Report r = d.getValue(Report.class);
                    myReports.add(r);
                    myAdapter = new ReportAdapter(myReports, getActivity().getApplicationContext());
                    myRecyclerView.setAdapter(myAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
    }

}
