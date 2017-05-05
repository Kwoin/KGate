package com.github.kwoin.kgate.smtp.sequencing.state;

import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.smtp.sequencing.SmtpRequestSequencer;
import com.github.kwoin.kgate.smtp.sequencing.callback.ReadDataCallback;


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
