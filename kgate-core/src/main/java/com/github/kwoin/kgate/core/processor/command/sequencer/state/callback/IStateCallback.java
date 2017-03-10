package com.github.kwoin.kgate.core.processor.command.sequencer.state.callback;

import com.github.kwoin.kgate.core.processor.command.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.processor.command.sequencer.state.AbstractState;


/**
 * @author P. WILLEMET
 */
public interface IStateCallback {

    int run(byte[] dataRead, IStateMachine stateMachine, AbstractState callingState);

}
