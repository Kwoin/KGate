package com.github.kwoin.kgate.core.processor.chain.command.sequencer;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.AbstractState;


/**
 * @author P. WILLEMET
 */
public class StateMachineSequencer implements IStateMachineSequencer {


    protected AbstractState[] states;
    protected int currentStateIndex;


    public StateMachineSequencer(AbstractState[] states) {

        this.states = states;
        currentStateIndex = 0;

    }


    @Override
    public ESequencerResult push(byte b) {

        currentStateIndex = states[currentStateIndex].push(b);
        switch(currentStateIndex) {
            case CUT:
                return ESequencerResult.CUT;
            case STOP:
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
}
