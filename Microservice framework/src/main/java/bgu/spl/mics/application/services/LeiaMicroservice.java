package bgu.spl.mics.application.services;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.Callbacks.StartCallback;
import bgu.spl.mics.application.Callbacks.TerminationCallback;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Attack;


/**
 * LeiaMicroservices Initialized with Attack objects, and sends them as  {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LeiaMicroservice extends MicroService {
	private Attack[] attacks;
    private Future future;
	
    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
		this.attacks = attacks;
    }

    @Override
    protected void initialize() {
        subscribeBroadcast(StartBroadcast.class, new StartCallback());
        sendBroadcast(new StartBroadcast(this));
        subscribeBroadcast(TerminationBroadcast.class, new TerminationCallback());
        synchronized (this) {
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void UpdateDiary(long value) {
        // Leia has no unique field to update, so the method is empty.
    }

    public void sendAttacks(){
        for (Attack i: attacks) {
            AttackEvent attackEvent = new AttackEvent(i);
            sendEvent(attackEvent);
        }
    }

    public void sendDeactivation(){

        future = sendEvent(new DeactivationEvent());
    }

    public void sendBombDestroyer(){
        future = sendEvent(new BombDestroyerEvent());
    }

    public void sendTerminate(){
        sendBroadcast(new TerminationBroadcast());
    }

    public boolean isCompleted(){
        return future.isDone();
    }

    public int totalAttacks(){
        return attacks.length;
    }
}
