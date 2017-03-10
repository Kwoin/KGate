package com.github.kwoin.kgate.core.processor.command.sequencer;

import com.github.kwoin.kgate.core.processor.command.sequencer.state.AbstractState;


/**
 * @author P. WILLEMET
 */
public abstract class StateMachineSequencer implements ISequencer, IStateMachine {


    protected AbstractState[] states;
    protected int currentStateIndex;
    protected boolean resetOnCut;


    public StateMachineSequencer(boolean resetOnCut) {

        currentStateIndex = 0;
        states = initializeStates();
        this.resetOnCut = resetOnCut;

    }


    @Override
    public ESequencerResult push(byte b) {

        currentStateIndex = states[currentStateIndex].push(b);
        switch(currentStateIndex) {
            case CUT:
                if(resetOnCut)
                    reset();
                return ESequencerResult.CUT;
            case STOP:
                if(resetOnCut)
                    reset();
                return ESequencerResult.STOP;
            default:
                return ESequencerResult.CONTINUE;
        }

    }


    @Override
    public void reset() {

        for (AbstractState state : states)
            state.reset();

        currentStateIndex = 0;

    }


    @Override
    public int getCurrentStateIndex() {

        return currentStateIndex;

    }


    @Override
    public void setCurrentStateIndex(int currentStateIndex) {

        this.currentStateIndex = currentStateIndex;

    }


    public abstract AbstractState[] initializeStates();

}
