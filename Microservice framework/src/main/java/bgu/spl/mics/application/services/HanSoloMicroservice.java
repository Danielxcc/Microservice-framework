package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.Callbacks.AttackCallback;
import bgu.spl.mics.application.Callbacks.TerminationCallback;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.TerminationBroadcast;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.holder;


/**
 * HanSoloMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class HanSoloMicroservice extends MicroService {

    public HanSoloMicroservice() {
        super("Han");
    }


    @Override
    protected void initialize() {
        subscribeEvent(AttackEvent.class, new AttackCallback());
        subscribeBroadcast(TerminationBroadcast.class, new TerminationCallback());

    }

    @Override
    public void UpdateDiary(long value) {
        if(holder.getInstance().doAttack()) {
            Diary.getInstance().setHanSoloFinish(value);
        }
    }
}
