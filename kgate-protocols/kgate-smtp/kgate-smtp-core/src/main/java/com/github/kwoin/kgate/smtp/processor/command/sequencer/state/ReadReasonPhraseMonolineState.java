package com.github.kwoin.kgate.smtp.processor.command.sequencer.state;

import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.SmtpResponseSequencer;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.callback.ReadReasonPhraseMonolineCallback;


/**
 * @author P. WILLEMET
 */
public class ReadReasonPhraseMonolineState extends ReadUntilSequenceState<SmtpResponseSequencer> {


    public ReadReasonPhraseMonolineState(SmtpResponseSequencer stateMachine) {

        super(stateMachine,
                "\r\n".getBytes(),
                null,
                new ReadReasonPhraseMonolineCallback(),
                null,
                true);

    }
}
