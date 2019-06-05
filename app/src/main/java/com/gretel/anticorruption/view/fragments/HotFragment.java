package com.gretel.anticorruption.view.fragments;

import com.gretel.anticorruption.R;

public class HotFragment extends ReportFragment {

    @Override
    protected void setQuery() {
        myQuery = myReportDatabase.orderByChild("rank").limitToLast(100);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_latest;
    }

    @Override
    protected void setText(Long count) {

    }
}
