package bgu.spl.mics.application.passiveObjects;

import bgu.spl.mics.Message;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MavenPair {
    /**
     * due to issues of maven with the Pair object, I've decided to create a Pair standalone object.
     */

    public MavenPair(Vector<Class> first, ConcurrentLinkedQueue<Message> second){
        First = first;
        Second = second;
    }

    public Vector<Class> getKey(){
        return First;
    }

    public ConcurrentLinkedQueue<Message> getValue(){
        return Second;
    }

    private Vector<Class> First;
    private ConcurrentLinkedQueue<Message> Second;
}
