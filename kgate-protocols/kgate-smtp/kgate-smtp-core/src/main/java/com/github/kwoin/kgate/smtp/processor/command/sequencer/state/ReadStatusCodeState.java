package com.github.kwoin.kgate.smtp.processor.command.sequencer.state;

import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.SmtpResponseSequencer;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.callback.ReadStatusCodeCallback;


/**
 * @author P. WILLEMET
 */
public class ReadStatusCodeState extends ReadUntilSequenceState<SmtpResponseSequencer> {


    public ReadStatusCodeState(SmtpResponseSequencer stateMachine) {

        super(stateMachine,
                " ".getBytes(),
                null,
                new ReadStatusCodeCallback(),
                null,
                true);

    }
}
