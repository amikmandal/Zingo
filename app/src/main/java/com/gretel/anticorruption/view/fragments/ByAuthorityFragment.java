package com.gretel.anticorruption.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.gretel.anticorruption.R;

public class ByAuthorityFragment extends ReportFragment {

    private String mySelectedAuthority;

    private TextView myReportCount;
    private Spinner mySelectAuthority;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        myReportCount = view.findViewById(R.id.text_authority);
        mySelectAuthority = view.findViewById(R.id.select_authority);

        setSpinner();

        return view;
    }

    @Override
    protected void setQuery() {
        myQuery = myReportDatabase.orderByChild("authority").equalTo(mySelectedAuthority).limitToFirst(100);
    }

    public void setText(Long count) {
        myReportCount.setText(count + " reports found");
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_by_authority;
    }

    private void setSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.agencies, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySelectAuthority.setAdapter(adapter);
        mySelectAuthority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mySelectedAuthority = parent.getItemAtPosition(position).toString();
                myReports.clear();
                initRecyclerView(myView);
                setQuery();
                myQuery.addListenerForSingleValueEvent(myListener);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mySelectedAuthority = "Select an item";
            }
        });
    }
}
