package com.github.kwoin.kgate.http.processor.chain.command.sequencer.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.StateMachineSequencer;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.http.processor.chain.command.sequencer.state.callback.HttpHeaderCallback;


/**
 * @author P. WILLEMET
 */
public class ReadHeadersState extends ReadUntilSequenceState {


    public ReadHeadersState(StateMachineSequencer stateMachine) {

        super(stateMachine,
                "\r\n".getBytes(),
                null,
                new HttpHeaderCallback(),
                null);

    }
}
