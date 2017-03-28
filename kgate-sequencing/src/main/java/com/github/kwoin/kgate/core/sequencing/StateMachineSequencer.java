package com.github.kwoin.kgate.core.sequencing;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;


/**
 * @author P. WILLEMET
 */
public abstract class StateMachineSequencer implements ISequencer, IStateMachine {


    protected AbstractState[] states;
    protected int currentStateIndex;
    protected IContext context;


    public StateMachineSequencer() {

        states = initializeStates();

    }


    @Override
    public void init(IContext context) {

        currentStateIndex = 0;
        this.context = context;
        for (AbstractState state : states)
            state.reset();

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
    public int getCurrentStateIndex() {

        return currentStateIndex;

    }


    @Override
    public void setCurrentStateIndex(int currentStateIndex) {

        this.currentStateIndex = currentStateIndex;

    }


    public abstract AbstractState[] initializeStates();

}
