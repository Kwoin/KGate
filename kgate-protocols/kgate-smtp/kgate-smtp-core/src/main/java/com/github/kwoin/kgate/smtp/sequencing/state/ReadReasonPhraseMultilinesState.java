package com.github.kwoin.kgate.smtp.sequencing.state;

import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.smtp.sequencing.SmtpResponseSequencer;
import com.github.kwoin.kgate.smtp.sequencing.callback.ReadReasonPhraseMultilinesCallback;


/**
 * @author P. WILLEMET
 */
public class ReadReasonPhraseMultilinesState extends ReadUntilSequenceState<SmtpResponseSequencer> {


    public ReadReasonPhraseMultilinesState(SmtpResponseSequencer stateMachine) {

        super(stateMachine,
                "\r\n".getBytes(),
                null,
                new ReadReasonPhraseMultilinesCallback(),
                null,
                true);

    }
}
