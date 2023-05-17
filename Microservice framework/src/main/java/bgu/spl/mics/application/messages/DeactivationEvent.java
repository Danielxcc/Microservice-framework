package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;

public class DeactivationEvent implements Event<Boolean> {
    /**
     * this is a "Marker" event, signaling R2D2 to deactivate the shields.
     */
    public DeactivationEvent(){

    }
}
