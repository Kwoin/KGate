package com.github.kwoin.kgate.core.sequencing;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;


/**
 * @author P. WILLEMET
 */
public abstract class StateMachineSequencer implements ISequencer, IStateMachine {


    protected AbstractState[] states;
    protected int currentStateIndex;
    protected IContext context;
    protected IoPoint inputPoint;


    public StateMachineSequencer() {

        states = initializeStates();

    }


    @Override
    public void init(IContext context, IoPoint inputPoint) {

        this.context = context;
        this.inputPoint = inputPoint;

    }


    @Override
    public ESequencerResult push(byte b) {

        int result = states[currentStateIndex].push(b);
        switch(result) {
            case CUT:
                return ESequencerResult.CUT;
            case STOP:
                return ESequencerResult.STOP;
            default:
                currentStateIndex = result;
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


    @Override
    public AbstractState[] getStates() {

        return states;

    }


    @Override
    public IContext getContext() {

        return context;

    }


    @Override
    public IoPoint getInputPoint() {

        return inputPoint;

    }


    @Override
    public void reset() {

        currentStateIndex = 0;
        for (AbstractState state : states)
            state.reset();

    }


    public abstract AbstractState[] initializeStates();

}
