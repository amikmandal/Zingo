package com.gretel.zingo.view.fragments;

import com.gretel.zingo.R;

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
