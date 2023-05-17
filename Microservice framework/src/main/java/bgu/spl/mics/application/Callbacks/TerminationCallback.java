package bgu.spl.mics.application.Callbacks;

import bgu.spl.mics.Callback;
import bgu.spl.mics.application.messages.TerminationBroadcast;

public class TerminationCallback implements Callback<TerminationBroadcast> {

    @Override
    public void call(TerminationBroadcast c) {
        /**
         * this callback is empty, because I've decided to terminate each thread without the callback.
         */
    }
}
