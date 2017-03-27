package com.github.kwoin.kgate.http.processor.command.sequencer.state;

import com.github.kwoin.kgate.core.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.sequencer.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.http.processor.command.sequencer.state.callback.InterpretTransferEncodingCallback;


/**
 * @author P. WILLEMET
 */
public class ReadTransferEncodingState extends ReadUntilSequenceState {


    public ReadTransferEncodingState(IStateMachine stateMachine) {

        super(stateMachine,
                "\r\n".getBytes(),
                null,
                new InterpretTransferEncodingCallback(),
                null,
                true);

    }
}
