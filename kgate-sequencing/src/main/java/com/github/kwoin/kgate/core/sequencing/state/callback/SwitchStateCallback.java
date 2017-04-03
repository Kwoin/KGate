package com.github.kwoin.kgate.core.sequencing.state.callback;

import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.core.sequencing.IStateMachine;


/**
 * @author P. WILLEMET
 */
public class SwitchStateCallback<T extends IStateMachine> implements IStateCallback<T> {


    private int targetState;
    private boolean resetCallingState;


    public SwitchStateCallback(int targetState, boolean resetCallingState) {

        this.targetState = targetState;
        this.resetCallingState = resetCallingState;

    }


    @Override
    public int run(byte[] dataRead, T stateMachine, AbstractState callingState) {

        if(resetCallingState)
            callingState.reset();

        return targetState;

    }



}
