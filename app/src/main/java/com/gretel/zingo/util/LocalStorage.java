package com.gretel.zingo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.gretel.zingo.model.Agent.User;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is used to store files locally in Android. Uses SharedPreference.
 * @author Amik Mandal
 */
public class LocalStorage {

    private SharedPreferences myStorage;
    private SharedPreferences.Editor myEditor;

    /**
     * Constructor to initialize instance variables
     * @param context specifies the context of the activity called from
     */
    public LocalStorage(Context context){
        myStorage = PreferenceManager.getDefaultSharedPreferences(context);
        myEditor = myStorage.edit();
        myEditor.apply();
    }

    /**
     * Method to save a string locally
     * @param key specifies the key used to save
     * @param value specifies the value to be stored
     */
    public void saveString(String key, String value){
        myEditor.putString(key,value).apply();
    }

    /**
     * Method to retrieve a string locally
     * @param key key that was used to save a data
     * @return the value retrieved
     */
    public String loadString(String key){
        return myStorage.getString(key, "");
    }

    /**
     * Method to store an user locally
     * @param u specifies the user to be saved
     */
    public void saveUser(User u) {

    }

    /**
     * Method to retrieve an user that is saved locally
     * @return the user stored locally
     */
    public User loadUser(){
        return null;
    }

    public String loadUserJSON() {
        return loadString("myUser");
    }

    /**
     * Method to delete saved data of User
     */
    public void removeUser(){;
    }

    /**
     * Method to edit saved data of User
     */
    public void editUser(User user){

    }

    /**
     * Method to check if user is logged in or not
     * @return true if logged in already, else false
     */
    public boolean checkIfUserPresent(){
        return false;
    }
}
