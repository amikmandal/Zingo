package com.gretel.anticorruption.model.Agent;

public class Authority extends Agent {

    private String myAuthorityName;
    private String myPosition;

    public Authority(Agent a, String authorityName, String position){
        super(a.getDisplayPicture(),a.getFirstName(),a.getLastName(),a.getID(),a.getEmail(),a.getNumber(),a.getLoginType());
        myAuthorityName = authorityName;
        myPosition = position;
    }

    public String getAuthorityName() { return  myAuthorityName; }

    public String getPosition() { return myPosition; }

}
