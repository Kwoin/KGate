package com.github.kwoin.kgate.http.processor.chain.command.sequencer.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.StateMachineSequencer;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.RecursiveState;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.callback.NextStateCallback;


/**
 * @author P. WILLEMET
 */
public class ReadHeadersState extends RecursiveState {


    public ReadHeadersState(StateMachineSequencer stateMachine) {

        super(stateMachine, new NextStateCallback(), null);

    }
}
