package bgu.spl.mics.application.passiveObjects;

import java.util.concurrent.ConcurrentHashMap;


/**
 * Passive object representing the resource manager.
 * <p>
 * This class must be implemented as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class Ewoks {
    private static Ewoks ewoks = null;

    public Ewoks(){
        Ewoks = new ConcurrentHashMap<>();
    }

    public static Ewoks getInstance(){
        if(ewoks == null){
            ewoks = new Ewoks();
        }
        return ewoks;
    }

    public void addEwok(int Serialnumber){
        Ewok e = new Ewok(Serialnumber);
        Ewoks.putIfAbsent(Serialnumber, e);
    }

    public boolean acquire(int Serialnumber){
        Ewok e = Ewoks.get(Serialnumber);
        if(e.isAvailable()){
            e.acquire();
            return true;
        }
        else{
            return false;
        }
    }

    public void release(int Serialnumber){
        Ewoks.get(Serialnumber).release();
    }

    private final ConcurrentHashMap<Integer, Ewok> Ewoks;
}
