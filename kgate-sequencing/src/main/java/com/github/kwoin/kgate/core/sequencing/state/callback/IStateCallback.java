package com.github.kwoin.kgate.core.sequencing.state.callback;

import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;


/**
 * @author P. WILLEMET
 */
public interface IStateCallback<T extends IStateMachine> {

    int run(byte[] dataRead, T stateMachine, AbstractState callingState);

}
