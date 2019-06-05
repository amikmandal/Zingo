package com.gretel.anticorruption.util;

import android.content.Context;
import android.os.AsyncTask;

import com.gretel.anticorruption.model.Agent.User;
import com.gretel.anticorruption.view.activities.MainActivity.MainActivity;

import java.lang.ref.WeakReference;

public class UserLoader extends AsyncTask<Void,Void,String> {

    private WeakReference<MainActivity> myMainActivityWeakReference;
    private LocalStorage myLocalStorage;

    public UserLoader(MainActivity mainActivity, Context context){
        myMainActivityWeakReference = new WeakReference<>(mainActivity);
        myLocalStorage = new LocalStorage(context);
    }

    @Override
    protected String doInBackground(Void... voids) {
        User u = myLocalStorage.loadUser();
        while (u==null){
            u = myLocalStorage.loadUser();
        }
        return u.getName();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        MainActivity mainActivity = myMainActivityWeakReference.get();
        if(mainActivity ==null|| mainActivity.isFinishing()){
            return;
        }

        s = "Hello " + s + "!";
        mainActivity.getNavHeaderTextView().setText(s);
    }
}
