package com.gretel.anticorruption.model.FormData;

import android.content.Context;
import android.os.Bundle;

import com.gretel.anticorruption.model.Agent.Agent;
import com.gretel.anticorruption.model.Agent.User;
import com.gretel.anticorruption.util.FirebaseManager;

abstract public class FormData {

    private String myDatabaseRoot;
    private Context myContext;
    Agent myAgent;
    String[] myRequirements;

    FormData(String databaseRoot, Context context){
        myDatabaseRoot = databaseRoot;
        myContext = context;
    }

    /**
     * This method calls setUser to initialize myUser and stores the user on Firebase.
     * @param data specifies the bundle data to be used to create myUser
     */
    public void makeAgent(Bundle data){
        setAgent(data);
        FirebaseManager firebaseManager = getFirebaseDatabase(myContext);
        firebaseManager.addUser(myAgent,myAgent.getLoginType());
    }

    protected abstract FirebaseManager getFirebaseDatabase(Context context);

    protected User setCommon(Bundle data){
        String firstName = data.getString("firstName");
        String lastName = data.getString("lastName");
        String name = firstName + " " + lastName;
        String facebookID = data.getString("id");
        String email = data.getString("email");
        String displayPicture = data.getString("profilePicture");
        String loginType = data.getString("loginType");

        String number = "";

        //data made from UserFormActivity
        for(int i=0; i<myRequirements.length; i++){
            switch (i){
                case 0:
                    number = data.getString(Integer.toString(i));
                    break;
            }
        }

        return new User(displayPicture,firstName,lastName,facebookID,email,number,loginType);
    }

    /**
     * This method initializes myUser
     * @param data specifies the Bundle data used to create myUser
     */
    abstract public void setAgent(Bundle data);

    /**
     * This is a getter method for myUser
     * @return the user formed at the end of form
     */
    public Agent getAgent() { return myAgent; }

    /**
     * This is a getter method to get one additional requirements.
     * @param index specifies the index whose requirement we want
     * @return the requirement at index position
     */
    public String getRequirement(int index){
        return myRequirements[index];
    }

    /**
     * This is a method to get the size of the list of the additional requirement.
     * @return the size of the list of the additional requirement.
     */
    public int getRequirementsSize(){
        return myRequirements.length;
    }

}
