package com.example.vit.popularmovies.communication;


import com.squareup.otto.Bus;

public class EventMessenger {

    static final String CLASS = EventMessenger.class.getSimpleName() + ": ";

    private static EventMessenger messenger;
    private static Bus bus;

    public static void init(){
        if(messenger == null){
            messenger = new EventMessenger();
        }
    }

    private EventMessenger(){
        EventMessenger.bus = BusProvider.getInstance();
    }

    static public void sendEvent(NetEvents event){
        EventMessenger.bus.post(event);
    }

    static public void sendEvent(NetEvents event, String receiver){
        EventMessenger.bus.post(new Event(event,receiver));
    }
}
