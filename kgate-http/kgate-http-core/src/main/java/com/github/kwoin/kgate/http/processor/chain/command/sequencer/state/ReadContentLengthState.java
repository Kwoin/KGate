package com.github.kwoin.kgate.http.processor.chain.command.sequencer.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.http.processor.chain.command.sequencer.state.callback.RegisterContentLengthCallback;


/**
 * @author P. WILLEMET
 */
public class ReadContentLengthState extends ReadUntilSequenceState {


    public ReadContentLengthState(IStateMachine stateMachine) {

        super(stateMachine,
                "\r\n".getBytes(),
                null,
                new RegisterContentLengthCallback(),
                null,
                true);

    }

}
