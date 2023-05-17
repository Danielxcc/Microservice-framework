package bgu.spl.mics.application.passiveObjects;

import bgu.spl.mics.MicroService;
import java.util.concurrent.atomic.AtomicInteger;

import bgu.spl.mics.application.services.*;

/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 * Do not add to this class nothing but a single constructor, getters and setters.
 */
public class Diary {
    private static Diary diary = null;

    private Diary(){
        totalAttacks = new AtomicInteger();
    }

    public static Diary getInstance(){
        if(diary == null){
            diary = new Diary();
        }
        return diary;
    }

    public AtomicInteger getTotalAttacks() {
        return totalAttacks;
    }

    public void setTotalAttacks(AtomicInteger totalAttacks) {
        this.totalAttacks = totalAttacks;
    }

    public void addAttack(){
        totalAttacks.incrementAndGet();
    }

    public long getHanSoloFinish() {
        return HanSoloFinish;
    }

    public void setHanSoloFinish(long hanSoloFinish) {
        HanSoloFinish = hanSoloFinish;
    }

    public long getC3POFinish() {
        return C3POFinish;
    }

    public void setC3POFinish(long c3POFinish) {
        C3POFinish = c3POFinish;
    }

    public long getR2D2Deactivate() {
        return R2D2Deactivate;
    }

    public void setR2D2Deactivate(long r2D2Deactivate) {
        R2D2Deactivate = r2D2Deactivate;
    }

    public long getLeiaTerminate() {
        return LeiaTerminate;
    }

    public void setLeiaTerminate() {
        LeiaTerminate = System.currentTimeMillis();
    }

    public long getHanSoloTerminate() {
        return HanSoloTerminate;
    }

    public void setHanSoloTerminate() {
        HanSoloTerminate = System.currentTimeMillis();
    }

    public long getC3POTerminate() {
        return C3POTerminate;
    }

    public void setC3POTerminate() {
        C3POTerminate = System.currentTimeMillis();
    }

    public long getR2D2Terminate() {
        return R2D2Terminate;
    }

    public void setR2D2Terminate() {
        R2D2Terminate = System.currentTimeMillis();
    }

    public long getLandoTerminate() {
        return LandoTerminate;
    }

    public void setLandoTerminate() {
        LandoTerminate = System.currentTimeMillis();
    }

    public void Terminated() {
        holder.getInstance().Countdown();
    }

    public void Setter(MicroService m){
        if(m instanceof LeiaMicroservice){
            setLeiaTerminate();
            Terminated();
        }
        if(m instanceof HanSoloMicroservice){
            setHanSoloTerminate();
            Terminated();
        }
        if(m instanceof C3POMicroservice){
            setC3POTerminate();
            Terminated();
        }
        if(m instanceof R2D2Microservice){
            setR2D2Terminate();
            Terminated();
        }
        if(m instanceof LandoMicroservice){
            setLandoTerminate();
            Terminated();
        }
    }

    private AtomicInteger totalAttacks;
    private long HanSoloFinish;
    private long C3POFinish;
    private long R2D2Deactivate;
    // Termination times.
    private long LeiaTerminate;
    private long HanSoloTerminate;
    private long C3POTerminate;
    private long R2D2Terminate;
    private long LandoTerminate;

}
