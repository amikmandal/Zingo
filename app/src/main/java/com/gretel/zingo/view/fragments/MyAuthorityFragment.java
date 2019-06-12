package com.gretel.zingo.view.fragments;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gretel.zingo.R;
import com.gretel.zingo.model.Agent.Authority;
import com.gretel.zingo.util.LocalStorage;

public class MyAuthorityFragment extends ReportFragment {

    @Override
    protected void setQuery() {
        LocalStorage localStorage = new LocalStorage(getContext());
        Authority a = localStorage.loadAuthority();
        DatabaseReference reportDatabase = FirebaseDatabase.getInstance().getReference("reports");
        myQuery = reportDatabase.orderByChild("authority").equalTo(a.getAuthorityName());
    }

    public void setText(Long count) {
        ;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_chat;
    }
}
