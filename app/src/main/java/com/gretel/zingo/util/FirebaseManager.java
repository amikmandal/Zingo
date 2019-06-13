package com.gretel.zingo.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.gretel.zingo.model.Agent.Agent;
import com.gretel.zingo.model.Agent.User;

/**
 * This class is responsible of most of the Firebase tasks that can be done outside an activity.
 * @author Amik Mandal
 * @date 2/22/2019
 */
public class FirebaseManager {

    private DatabaseReference databaseReference;
    private Context myContext;

    public FirebaseManager(String database, Context context){
        databaseReference = FirebaseDatabase.getInstance().getReference(database);
        myContext = context;
    }

    public void addUser(Agent a, String loginType){
        Gson gson = new Gson();
        String json = gson.toJson(a);
        if(a instanceof User)
            databaseReference.child(loginType).child(a.getID()).setValue(json);
        else
            databaseReference.child(a.getID()).setValue(json);
    }

    public void getUser(String loginType, String id){

        databaseReference = databaseReference.child(loginType).child(id);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Gson gson = new Gson();
                String json = dataSnapshot.getValue(String.class);
                User u = gson.fromJson(json, User.class);

                LocalStorage localStorage = new LocalStorage(myContext);
                localStorage.saveUser(u);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void editUser(User user){
        databaseReference.child(user.getLoginType()).child(user.getID()).removeValue();
        addUser(user,user.getLoginType());
    }

}
