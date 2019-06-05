package com.gretel.anticorruption.view.fragments;

import android.widget.TextView;

import com.gretel.anticorruption.R;
import com.gretel.anticorruption.model.Agent.User;
import com.gretel.anticorruption.util.LocalStorage;

public class MyReportsFragment extends ReportFragment {

    @Override
    protected void setQuery() {
        LocalStorage localStorage = new LocalStorage(getContext());
        User u = localStorage.loadUser();
        myQuery = myReportDatabase.orderByChild("user").equalTo(u.getID());
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_my_reports;
    }

    @Override
    protected void setText(Long count) {
        TextView textView = myView.findViewById(R.id.text_me);
        textView.setText(count + " reports made");
    }
}
