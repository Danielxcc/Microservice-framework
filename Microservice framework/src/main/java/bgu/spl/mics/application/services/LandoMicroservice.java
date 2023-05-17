package bgu.spl.mics.application.services;


import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.Callbacks.TerminationCallback;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.TerminationBroadcast;
import bgu.spl.mics.application.Callbacks.BombDestroyerCallback;
import bgu.spl.mics.application.passiveObjects.holder;

/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {

    public LandoMicroservice(long duration) {
        super("Lando");
        Duration = duration;
    }

    @Override
    protected void initialize() {
       subscribeEvent(BombDestroyerEvent.class, new BombDestroyerCallback());
       subscribeBroadcast(TerminationBroadcast.class, new TerminationCallback());
        holder.getInstance().setLandoDuration(Duration);
    }

    @Override
    public void UpdateDiary(long value) {
        // Lando has no unique field to update, so the method is empty.
    }

    private final long Duration;
}
