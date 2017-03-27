package com.github.kwoin.kgate.core.sequencer.state.callback;

import com.github.kwoin.kgate.core.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.sequencer.state.AbstractState;


/**
 * @author P. WILLEMET
 */
public class SwitchStateCallback implements IStateCallback {


    private int targetState;
    private boolean resetCallingState;


    public SwitchStateCallback(int targetState, boolean resetCallingState) {

        this.targetState = targetState;
        this.resetCallingState = resetCallingState;

    }


    @Override
    public int run(byte[] dataRead, IStateMachine stateMachine, AbstractState callingState) {

        if(resetCallingState)
            callingState.reset();

        return targetState;

    }
}
