package com.github.kwoin.kgate.http.sequencing.state_old.callback;


import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.core.sequencing.state.callback.IStateCallback;


/**
 * @author P. WILLEMET
 */
public class IsEndCallback implements IStateCallback {


    @Override
    public int run(byte[] dataRead, IStateMachine stateMachine, AbstractState callingState) {

        callingState.reset();
        return dataRead.length == 2 ? 3 : 0;

    }

}
