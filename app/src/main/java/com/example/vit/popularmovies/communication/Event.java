package com.example.vit.popularmovies.communication;

/**
 *
 */
public class Event {

    private NetEvents event;
    private String receiver;

    public Event(NetEvents event, String receiver){
        this.event = event;
        this.receiver = receiver;
    }

    public NetEvents getEvent(){
        return this.event;
    }

    public String getReceiver(){
        return this.receiver;
    }
}
