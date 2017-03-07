package com.github.kwoin.kgate.core.processor.chain.command.sequencer;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.AbstractState;


/**
 * @author P. WILLEMET
 */
public abstract class StateMachineSequencer implements ISequencer, IStateMachine {


    protected AbstractState[] states;
    protected int currentStateIndex;


    public StateMachineSequencer() {

        currentStateIndex = 0;
        states = initializeStates();

    }


    @Override
    public ESequencerResult push(byte b) {

        currentStateIndex = states[currentStateIndex].push(b);
        switch(currentStateIndex) {
            case CUT:
                reset();
                return ESequencerResult.CUT;
            case STOP:
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
