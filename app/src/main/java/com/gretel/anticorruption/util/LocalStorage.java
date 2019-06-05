package com.gretel.anticorruption.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.gretel.anticorruption.model.Agent.Authority;
import com.gretel.anticorruption.model.Agent.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        Gson gson = new Gson();
        String json = gson.toJson(u);
        saveString("myUser", json);

    }

    /**
     * Method to retrieve an user that is saved locally
     * @return the user stored locally
     */
    public User loadUser(){

        Gson gson = new Gson();
        String json = loadUserJSON();
        return gson.fromJson(json, User.class);

    }

    public String loadUserJSON() {
        return loadString("myUser");
    }

    /**
     * Method to delete saved data of User
     */
    public void removeUser(){
        myEditor.remove("myUser").apply();
    }

    public void editUser(User user){
        removeUser();
        saveUser(user);
    }

    public boolean checkIfUserPresent(){
        String checkJSON = loadUserJSON();
        if (checkJSON.equals("")){
            return false;
        }
        return true;
    }

    /**
     * Method to store an authority locally
     * @param r specifies the authority to be saved
     */
    public void saveAuthority(Authority r) {

        Gson gson = new Gson();
        String json = gson.toJson(r);
        saveString("myAuthority", json);

    }

    /**
     * Method to retrieve an user that is saved locally
     * @return the authority stored locally
     */
    public Authority loadAuthority(){

        Gson gson = new Gson();
        String json = loadAuthorityJSON();
        return gson.fromJson(json, Authority.class);

    }

    public String loadAuthorityJSON() {
        return loadString("myAuthority");
    }

    /**
     * Method to delete saved data of Authority
     */
    public void removeAuthority(){
        myEditor.remove("myAuthority").apply();
    }

    public void editAuthority(Authority r){
        removeUser();
        saveAuthority(r);
    }

    public boolean checkIfAuthorityPresent(){
        String checkJSON = loadAuthorityJSON();
        if (checkJSON.equals("")){
            return false;
        }
        return true;
    }

    public void addReportToUser(String reportID) {
        String[] oldReports = loadReports();
        ArrayList<String> newReports;
        if(oldReports==null)
            newReports = new ArrayList<>();
        else
            newReports = new ArrayList<String>(Arrays.asList(oldReports));
        newReports.add(reportID);
        saveReports(newReports.toArray(new String[0]));

    }

    private void saveReports(String[] reports) {
        Gson gson = new Gson();
        String json = gson.toJson(reports);
        saveString("reports", json);
    }

    private String[] loadReports() {
        Gson gson = new Gson();
        String json = loadReportsJSON();
        return gson.fromJson(json, String[].class);
    }

    private String loadReportsJSON() {
        return loadString("reports");
    }
}
