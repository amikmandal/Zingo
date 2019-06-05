package com.gretel.anticorruption.model.Chat;


import java.util.Date;

import static com.gretel.anticorruption.model.Chat.Text.MessageStatus.SENDING;

abstract public class Text {

    enum MessageStatus {
        SENDING,SENT,DELIVERED,SEEN
    }

    private Date myDate;
    private String mySender;
    private MessageStatus myStatus;

    public Text(Date date, String sender){
        myDate = date;
        mySender = sender;
        myStatus = SENDING;
    }

    abstract public Object getMessage();

    public Date getDate(){
        return myDate;
    }

    public void setStatus(MessageStatus status){
        myStatus = status;
    }

    public String getSender(){
        return mySender;
    }

    public MessageStatus getStatus(){
        return myStatus;
    }

}
