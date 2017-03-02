package com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.callback;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.StateMachineSequencer;


/**
 * @author P. WILLEMET
 */
public class NextStateCallback implements IStateCallback {


    @Override
    public ESequencerResult run(byte[] dataRead, StateMachineSequencer stateMachine) {

        stateMachine.next();

        return ESequencerResult.CONTINUE;

    }

}
