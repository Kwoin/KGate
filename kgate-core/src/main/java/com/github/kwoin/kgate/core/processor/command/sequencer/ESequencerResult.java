package com.github.kwoin.kgate.core.processor.command.sequencer;

/**
 * @author P. WILLEMET
 */
public enum ESequencerResult {

    STOP(0),
    CONTINUE(1),
    CUT(3);


    private int priority;


    ESequencerResult(int prority) {

        this.priority = prority;

    }


    public int getPriority() {

        return priority;

    }

}
