package com.gretel.anticorruption.model.Agent;

/**
 * This class represents the user.
 * @author Amik Mandal
 * @date 2/2/2019
 */
abstract public class Agent {

    private String myFirstName;
    private String myLastName;
    private String myID;
    private String myEmail;
    private String myNumber;
    private String myDisplayPicture;
    private String myLoginType;

    /**
     * Constructor to fill details for User
     */
    public Agent(String displayPicture, String firstName, String lastName, String facebookID, String email, String number,String loginType){

        myFirstName = firstName;
        myLastName = lastName;
        myNumber = number;
        myID = facebookID;
        myEmail = email;
        myDisplayPicture = displayPicture;
        myLoginType = loginType;

    }

    public boolean equals (Agent other){
        if(!this.myFirstName.equals(other.getFirstName()))
            return false;
        if(!this.myLastName.equals(other.getLastName()))
            return false;
        if(!this.myNumber.equals(other.myNumber))
            return false;
        if(!this.myID.equals(other.myID))
            return false;
        if(!this.myEmail.equals(other.myEmail))
            return false;
        if(!this.myDisplayPicture.equals(other.myDisplayPicture))
            return false;
        if(!this.myLoginType.equals(other.myLoginType))
            return false;
        return true;
    }

    public static String makeFirstName(String name){
        return name.split(" ")[0];
    }

    public static String makeLastName(String name){
        return name.replaceAll(makeFirstName(name)+" ","");
    }

    public String getName(){
        return myFirstName + " " + myLastName;
    }

    public String getNumber(){
        return myNumber;
    }

    public String getEmail(){
        return myEmail;
    }

    public String getDisplayPicture(){
        return myDisplayPicture;
    }

    public String getID() {return myID;}

    public String getLoginType() {return myLoginType;}

    public String getFirstName() {
        return myFirstName;
    }

    public String getLastName() {
        return myLastName;
    }

}
