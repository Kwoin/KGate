package com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.callback;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.StateMachineSequencer;


/**
 * @author P. WILLEMET
 */
public interface IStateCallback {

    ESequencerResult run(byte[] dataRead, StateMachineSequencer stateMachine);

}
