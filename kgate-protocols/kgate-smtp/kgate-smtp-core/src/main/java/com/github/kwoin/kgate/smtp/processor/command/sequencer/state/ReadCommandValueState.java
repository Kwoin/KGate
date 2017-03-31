package com.github.kwoin.kgate.smtp.processor.command.sequencer.state;

import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.SmtpRequestSequencer;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.callback.ReadCommandValueCallback;


/**
 * @author P. WILLEMET
 */
public class ReadCommandValueState extends ReadUntilSequenceState<SmtpRequestSequencer> {


    public ReadCommandValueState(SmtpRequestSequencer stateMachine) {

        super(stateMachine,
                "\r\n".getBytes(),
                null,
                new ReadCommandValueCallback(),
                null,
                true);
    }
}
