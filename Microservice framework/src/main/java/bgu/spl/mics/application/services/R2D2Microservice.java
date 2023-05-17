package bgu.spl.mics.application.services;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.Callbacks.TerminationCallback;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.TerminationBroadcast;
import bgu.spl.mics.application.Callbacks.DeactivationCallback;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.holder;

/**
 * R2D2Microservices is in charge of the handling {@link DeactivationEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link DeactivationEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class R2D2Microservice extends MicroService {

    public R2D2Microservice(long duration) {
        super("R2D2");
        Duration = duration;
        times = 1;
    }

    @Override
    protected void initialize() {
        subscribeEvent(DeactivationEvent.class, new DeactivationCallback());
        subscribeBroadcast(TerminationBroadcast.class, new TerminationCallback());
        holder.getInstance().setR2D2Duration(Duration);
    }

    @Override
    public void UpdateDiary(long value) {
        if(times > 0) {
            Diary.getInstance().setR2D2Deactivate(System.currentTimeMillis());
            times--;
        }
    }

    private final long Duration;
    private int times;
}
