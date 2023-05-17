package bgu.spl.mics.application.Callbacks;

import bgu.spl.mics.Callback;
import bgu.spl.mics.application.messages.StartBroadcast;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.holder;
import bgu.spl.mics.application.services.LeiaMicroservice;

public class StartCallback implements Callback<StartBroadcast> {

    @Override
    public void call(StartBroadcast c) {
        /**
         * leia's send broadcast.
         * firstly, leia sends the AttackEvents, then she waits for them to complete, then she sends the DeactivationEvent.
         * then she waits for R2D2 to update his diary Deactivation field, then she sends the BombDestroyerEvent.
         * then she waits until Lando updates a special boolean in the holder object, then she sends the terminateBroadcast.
         */
        LeiaMicroservice m = c.getM();
        m.sendAttacks(); //leia sends the AttackEvents.
        Diary diary = Diary.getInstance();
        while(diary.getTotalAttacks().intValue() < m.totalAttacks()){} //leia waits for completion of AttackEvents.
        holder.getInstance().setAttack(false);
        m.sendDeactivation(); //leia sends DeactivationEvent.
        // leia waits for R2D2 to finish.
        try {
            holder.getInstance().getR2D2Latch().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m.sendBombDestroyer(); // leia sends BombDestroyerEvent.
        // leia waits for Lando to finish.
        try {
            holder.getInstance().getLandoLatch().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m.sendTerminate(); // leia sends terminateBroadcast.
    }
}
