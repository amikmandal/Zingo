package com.gretel.anticorruption.model.Chat;

import java.util.ArrayList;

public class Chat {

    private ArrayList<ArrayList<Text>> myChat;
    private String myChatID;
    private Integer mySize;

    public Chat(String chatID){
        myChatID = chatID;
        myChat = new ArrayList<>();
        mySize = 0;
    }

    public void addText(Text text){
        ArrayList<Text> currTextSet;
        if(getSize()>0) {
            currTextSet = myChat.get(getSize() - 1);
            currTextSet.add(text);
        } else {
            currTextSet = new ArrayList<>();
            currTextSet.add(text);
            myChat.add(currTextSet);
            mySize++;
        }
    }

    //public void removeText(Text text){
      //  myChat.remove(text);
    //}

    //public String getChatID(){
      //  return myChatID;
    //}

    public ArrayList<ArrayList<Text>> getChat() { return myChat; }

    public Integer getSize(){
        return mySize;
    }

}
