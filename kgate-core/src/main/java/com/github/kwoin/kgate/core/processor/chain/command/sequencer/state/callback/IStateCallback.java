package com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.callback;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.AbstractState;


/**
 * @author P. WILLEMET
 */
public interface IStateCallback {

    int run(byte[] dataRead, IStateMachine stateMachine, AbstractState callingState);

}
