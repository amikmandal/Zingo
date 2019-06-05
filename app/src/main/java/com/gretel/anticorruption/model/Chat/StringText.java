package com.gretel.anticorruption.model.Chat;

import java.util.Date;

public class StringText extends Text {

    private String myMessage;

    public StringText(String message, Date date, String sender){
        super(date,sender);
        myMessage = message;
    }

    @Override
    public String getMessage() {
        return myMessage;
    }
}
