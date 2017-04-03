package com.github.kwoin.kgate.smtp.processor.command.sequencer.state;

import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.SmtpRequestSequencer;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.callback.ReadDataCallback;


/**
 * @author P. WILLEMET
 */
public class ReadDataState extends ReadUntilSequenceState<SmtpRequestSequencer> {


    public ReadDataState(SmtpRequestSequencer stateMachine) {

        super(stateMachine,
                "\r\n.\r\n".getBytes(),
                null,
                new ReadDataCallback(),
                null,
                true);

    }
}
