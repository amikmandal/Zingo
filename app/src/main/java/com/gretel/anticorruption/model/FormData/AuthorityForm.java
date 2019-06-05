package com.gretel.anticorruption.model.FormData;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.gretel.anticorruption.model.Agent.Authority;
import com.gretel.anticorruption.model.Agent.User;
import com.gretel.anticorruption.util.FirebaseManager;

public class AuthorityForm extends FormData {

    private static final String AUTHORITY_NAME = "What is the authority you represent?";
    private static final String POSITION = "What is your designation?";

    public AuthorityForm(Context context){
        super("user",context);
        String[] requirements = {AUTHORITY_NAME,POSITION};
        myRequirements = requirements;
    }

    @Override
    protected FirebaseManager getFirebaseDatabase(Context context) {
        return new FirebaseManager("authority", context);
    }

    @Override
    public void setAgent(Bundle data) {
        String userJson = data.getString("userData");
        Gson gson = new Gson();
        User u = gson.fromJson(userJson,User.class);
        String authorityName = "", position = "", field = "";
        Double rating = data.getDouble("rating");
        for(int i=0; i<myRequirements.length; i++){
            switch (i){
                case 0:
                    authorityName = data.getString(Integer.toString(i));
                    break;
                case 1:
                    position = data.getString(Integer.toString(i));
                    break;
            }
        }
        myAgent = new Authority(u,authorityName,position);
    }

    public static String getAuthorityName() {
        return AUTHORITY_NAME;
    }
}
