package com.github.kwoin.kgate.sequencing.state.callback;

import com.github.kwoin.kgate.sequencing.IStateMachine;
import com.github.kwoin.kgate.sequencing.state.AbstractState;


/**
 * @author P. WILLEMET
 */
public interface IStateCallback {

    int run(byte[] dataRead, IStateMachine stateMachine, AbstractState callingState);

}
