package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.application.services.LeiaMicroservice;

public class StartBroadcast implements Broadcast {
    /**
     * this Broadcast holds the {@link LeiaMicroservice} leia
     * @param M         leia.
     */
    public StartBroadcast(LeiaMicroservice M){
        m = M;
    }

    public LeiaMicroservice getM() {
        return m;
    }

    private LeiaMicroservice m;
}
