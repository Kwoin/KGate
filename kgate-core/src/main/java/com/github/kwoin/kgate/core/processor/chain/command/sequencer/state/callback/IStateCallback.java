package com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.callback;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.IStateMachineSequencer;


/**
 * @author P. WILLEMET
 */
public interface IStateCallback {

    int run(byte[] dataRead, IStateMachineSequencer stateMachineSequencer);

}
