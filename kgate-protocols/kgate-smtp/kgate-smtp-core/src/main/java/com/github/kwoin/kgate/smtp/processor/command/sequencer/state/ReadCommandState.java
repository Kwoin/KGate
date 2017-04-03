package com.github.kwoin.kgate.smtp.processor.command.sequencer.state;

import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.SmtpRequestSequencer;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.callback.ReadCommandCallback;


/**
 * @author P. WILLEMET
 */
public class ReadCommandState extends ReadUntilSequenceState<SmtpRequestSequencer> {


    public ReadCommandState(SmtpRequestSequencer stateMachine) {

        super(stateMachine,
                "\r\n".getBytes(),
                null,
                new ReadCommandCallback(),
                null,
                true);

    }
}
