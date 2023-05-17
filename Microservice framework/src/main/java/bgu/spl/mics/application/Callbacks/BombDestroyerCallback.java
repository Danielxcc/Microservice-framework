package bgu.spl.mics.application.Callbacks;

import bgu.spl.mics.Callback;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.passiveObjects.holder;

public class BombDestroyerCallback implements Callback<BombDestroyerEvent> {
    /**
     * Lando simulates the attack by sleeping, then he updates the holder field.
     * @param c
     */
    @Override
    public void call(BombDestroyerEvent c) {
        synchronized (this) {
            long duration = holder.getInstance().getLandoDuration();
            try {
                wait(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            holder.getInstance().LandoFinish();
        }

    }
}
