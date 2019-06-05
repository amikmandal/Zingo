package com.gretel.anticorruption.util;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is responsible for parsing JSON objects.
 * @author Amik Mandal
 */
public class JSONParser {

    private static final String EMAIL = "email";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String ID = "id";

    /**
     * This method reads a URL from a JSON object. This is currently used for getting facebook profiles.
     * @param userData specifies the JSON object
     * @return the URL read
     */
    private URL readURL(JSONObject userData){
        URL readURL = null;
        try{
            readURL = new URL("https://graph.facebook.com/"+userData.getString(ID)+"/picture?width=250&height=250");
        }catch (JSONException e){
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return readURL;
    }

    /**
     * This method reads the string of a particular field in a JSON Object
     * @param userData specifies the JSON Object
     * @param field specifies the field whose String we want
     * @return the string read
     */
    private String readString(JSONObject userData,String field){
        String readString = "";
        try{
            readString = userData.getString(field);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return readString;
    }

    /**
     * This method reads Long in a JSON Object. THis method currently is just used for reading ID.
     * @param userData specifies the JSON object
     * @return the ID read
     */
    private Long readLong(JSONObject userData){
        Long readLong = (long) 0;
        try{
            readLong = userData.getLong(ID);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return readLong;
    }

    /**
     * This method reads ID from a JSON object and returns a String version of it. A separate method
     * for this was developed to better handle the case of returning users.
     * @param userData specifies the JSON object
     * @return the string version of the ID if available, if not then return empty string.
     */
    public String readID(JSONObject userData){

        //Exceptions can be added here
        try{
            String test = Long.toString(userData.getLong(ID));
            return test;
        } catch (JSONException e){
            return "Failed";
        }
    }

    /**
     * This method makes the initial set of data for user.
     * @param userData specifies the JSON Object
     * @return the Bundle containing key, value pair for every detail of User that can be retrieved
     * from the JSON Object.
     */
    public Bundle makePreliminaryUserData(JSONObject userData, String loginType) {

        Bundle userBundle = new Bundle();

        userBundle.putString("firstName", readString(userData,FIRST_NAME));
        userBundle.putString("lastName", readString(userData,LAST_NAME));
        userBundle.putString("id", readLong(userData).toString());
        userBundle.putString("profilePicture", readURL(userData).toString());
        userBundle.putString("email", readString(userData,EMAIL));

        userBundle.putString("loginType", loginType);

        return userBundle;

    }
}
