package com.anshul.budchat.Models;

public class MessagesModel {
    String uId,message;
    long timeStamp;

    public MessagesModel(String uId, String message, long timeStamp) {
        this.uId = uId;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public MessagesModel(String uId, String message) {
        this.uId = uId;
        this.message = message;
    }

    public MessagesModel(){}

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
