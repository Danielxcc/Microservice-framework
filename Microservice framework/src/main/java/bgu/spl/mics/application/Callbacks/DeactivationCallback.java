package bgu.spl.mics.application.Callbacks;

import bgu.spl.mics.Callback;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.passiveObjects.holder;

public class DeactivationCallback implements Callback<DeactivationEvent> {

    @Override
    public void call(DeactivationEvent c) {
        synchronized (this){
            long duration = holder.getInstance().getR2D2Duration();
            try {
                wait(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            holder.getInstance().R2D2Finish();
        }
    }
}
