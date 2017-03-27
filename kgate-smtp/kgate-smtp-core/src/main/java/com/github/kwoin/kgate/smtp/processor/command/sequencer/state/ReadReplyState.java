package com.github.kwoin.kgate.smtp.processor.command.sequencer.state;

import com.github.kwoin.kgate.core.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.sequencer.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.state.callback.InterpretReplyCallback;


/**
 * @author P. WILLEMET
 */
public class ReadReplyState extends ReadUntilSequenceState {


    public ReadReplyState(IStateMachine stateMachine) {

        super(stateMachine,
                "\r\n".getBytes(),
                null,
                new InterpretReplyCallback(),
                null,
                true);

    }
}
