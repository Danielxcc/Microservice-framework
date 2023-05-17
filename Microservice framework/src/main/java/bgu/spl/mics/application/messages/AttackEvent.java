package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Attack;
import java.util.Collections;
import java.util.List;

public class AttackEvent implements Event<Boolean> {
    /**
     * this Event holds the information about an attack.
      */
    public AttackEvent(){

    }

    public AttackEvent(Attack attack){
	    Serials = attack.getSerials();
	    Collections.sort(Serials);
	    Duration = attack.getDuration();
    }

    public AttackEvent(List<Integer> list, int duration){
	    Serials = list;
        Collections.sort(Serials);
	    Duration = duration;
    }

    public List<Integer> getSerials(){
	    return Serials;
    }

    public int getDuration() {
        return Duration;
    }

    public Boolean result(){
	    return true;
    }

    private List<Integer> Serials;
	private int Duration;
}
