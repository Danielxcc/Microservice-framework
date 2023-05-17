package bgu.spl.mics.application.passiveObjects;

import java.util.concurrent.Semaphore;

/**
 * Passive data-object representing a forest creature summoned when HanSolo and C3PO receive AttackEvents.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Ewok {
	int serialNumber;
	boolean available;
	private Semaphore semaphore = new Semaphore(1, true);
	
    public Ewok(int serialNumber){
        this.serialNumber = serialNumber;
        this.available = true;
    }

    /**
     * Acquires an Ewok
     */
    public void acquire() {
        try {
            semaphore.acquire();
            available = false;
        }
        catch(InterruptedException e){

        }

    }

    /**
     * release an Ewok
     */
    public void release() {
        semaphore.release();
    	available = true;
    }

    /**
     * Checks if the Ewok is available to be acquired.
     */
    public boolean isAvailable() {
        return available;
    }
}
