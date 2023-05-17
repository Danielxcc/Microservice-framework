package bgu.spl.mics.application.passiveObjects;

import java.util.concurrent.CountDownLatch;

public class holder {
    /**
     * this Object holds multiple things, like the countdownlatch and the doAttack boolean for C3PO and Han-Solo.
     * EndLatch - a countdown latch which makes sure that all threads terminate at the same time.
     * latch - a countdown latch which continues the main method after every thread have terminated.
     * Attack - a boolean which makes sure that the attackers won't update the diary fields after finishing all the attacks.
     * R2D2/Lando duration - a long which holds the duration of the corresponding microservice's action duration.
     * R2D2/Lando latch - a countdown latch for leia.
     */
    private static holder holdeR = null;
    public static holder getInstance(){
        if(holdeR == null){
            holdeR = new holder();
        }
        return holdeR;
    }

    private holder(){
        Attack = true;
        R2D2Latch = new CountDownLatch(1);
        LandoLatch = new CountDownLatch(1);
    }

    public boolean doAttack() {
        return Attack;
    }

    public void setAttack(boolean b){
        Attack = b;
    }

    public void setlatch(CountDownLatch l){
        latch = l;
    }

    public void setEndLatch(CountDownLatch endLatch) {
        this.endLatch = endLatch;
    }

    public CountDownLatch getEndLatch(){
        return endLatch;
    }

    public void EndCountDown(){
        endLatch.countDown();
    }

    public CountDownLatch getR2D2Latch() {
        return R2D2Latch;
    }

    public void R2D2Finish(){
        R2D2Latch.countDown();
    }

    public CountDownLatch getLandoLatch() {
        return LandoLatch;
    }

    public void LandoFinish(){
        LandoLatch.countDown();
    }

    public void Countdown(){
        latch.countDown();
    }

    public long getR2D2Duration() {
        return R2D2Duration;
    }

    public void setR2D2Duration(long r2D2Duration) {
        R2D2Duration = r2D2Duration;
    }

    public long getLandoDuration() {
        return LandoDuration;
    }

    public void setLandoDuration(long landoDuration) {
        LandoDuration = landoDuration;
    }

    private CountDownLatch latch;
    private CountDownLatch endLatch;
    private CountDownLatch R2D2Latch;
    private CountDownLatch LandoLatch;
    private boolean Attack;
    private long R2D2Duration;
    private long LandoDuration;
}
