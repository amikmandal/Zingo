package com.gretel.zingo.util;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gretel.zingo.model.Agent.User;

/**
 * This class is responsible of most of the Firebase tasks that can be done outside an activity.
 */
public class FirebaseManager {

    private DatabaseReference databaseReference;
    private Context myContext;

    public FirebaseManager(String database, Context context){
        databaseReference = FirebaseDatabase.getInstance().getReference(database);
        myContext = context;
    }

    /**
     * Method to add User to firebase
     * @param user specifies the user to be added
     */
    public void addUser(User user){
    }

    /**
     * Method to fetch User from firebase
     * @param id specifies the id of the user
     */
    public void getUser(String id){

    }

    /**
     * Method to edit existing user
     * @param user specifies the user with the new data
     */
    public void editUser(User user){
    }

}
