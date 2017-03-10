package com.github.kwoin.kgate.http.processor.command.sequencer.state;

import com.github.kwoin.kgate.core.processor.command.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.processor.command.sequencer.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.http.processor.command.sequencer.state.callback.RegisterContentLengthCallback;


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
