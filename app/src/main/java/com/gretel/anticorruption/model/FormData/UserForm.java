package com.gretel.anticorruption.model.FormData;

import android.content.Context;
import android.os.Bundle;

import com.gretel.anticorruption.util.FirebaseManager;

/**
 * This class helps in keeping track of all the additional data we need for User which we are unable
 * to get from login methods.
 * @author Amik Mandal
 * @date 2/22/2019
 */
public class UserForm extends FormData {

    private static final String PHONE_NUMBER = "What is your Phone Number?";

    public UserForm(Context context){
       super("user",context);
       String[] requirements = {PHONE_NUMBER};
       myRequirements = requirements;
    }

    @Override
    protected FirebaseManager getFirebaseDatabase(Context context) {
        return new FirebaseManager("user",context);
    }

    public void setAgent(Bundle data){
        myAgent = setCommon(data);
    }

    /**
     * This is a getter method to get the static final PHONE_NUMBER
     * @return the string for PHONE_NUMBER
     */
    public static String getPhoneNumber() {
        return PHONE_NUMBER;
    }
}