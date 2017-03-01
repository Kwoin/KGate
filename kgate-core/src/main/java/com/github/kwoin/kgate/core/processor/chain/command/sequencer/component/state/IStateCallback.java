package com.github.kwoin.kgate.core.processor.chain.command.sequencer.component.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.component.StateMachineSequencerComponent;


/**
 * @author P. WILLEMET
 */
public interface IStateCallback {

    ESequencerResult run(byte[] dataRead, StateMachineSequencerComponent stateMachine);

}
