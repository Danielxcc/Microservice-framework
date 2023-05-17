package bgu.spl.mics.application.Callbacks;

import bgu.spl.mics.Callback;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;

import java.util.*;

public class AttackCallback implements Callback<AttackEvent> {

    @Override
    public void call(AttackEvent a) {
        /**
         * first, we set up the needed variables.
         */
        long duration = a.getDuration(); // the attack duration.
        List<Integer> Serials = a.getSerials(); // the serials of the Ewoks needed for the attack.
        Vector<Integer> Bwoks = new Vector<>(); // a vector copy of Serials.
        for(Integer i : Serials){
            Bwoks.add(i);
        }
        Ewoks ewoks = Ewoks.getInstance(); // the Ewoks instance.

        /**
         * then, we iterate over our needed ewoks, if we acquired a ewok, we remove it's serial from the serials list.
         * else, we continue to try and acquire the ewoks.
         */
        while(Serials.size() > 0) {
                Iterator<Integer> iter = Serials.iterator();
                while(iter.hasNext()) {
                    int i = iter.next();
                    if (ewoks.acquire(i)){
                        iter.remove();
                    }
                    else{
                        break;
                    }
                }
            }
            // after acquiring every ewok needed, it's time to sleep
            try {
                Thread.sleep(duration);
            }
            catch(InterruptedException e){}

            //now to release every ewok
            for(Integer i : Bwoks){
                ewoks.release(i);
            }
        // after the attack is done, increment the totalAttacks counter.
        Diary.getInstance().addAttack();
    }
}
