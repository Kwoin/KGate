package com.github.kwoin.kgate.core.sequencer.state.callback;

import com.github.kwoin.kgate.core.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.sequencer.state.AbstractState;


/**
 * @author P. WILLEMET
 */
public interface IStateCallback {

    int run(byte[] dataRead, IStateMachine stateMachine, AbstractState callingState);

}
