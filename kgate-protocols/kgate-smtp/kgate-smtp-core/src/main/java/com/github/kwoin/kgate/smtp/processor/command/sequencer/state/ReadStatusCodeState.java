package com.github.kwoin.kgate.smtp.processor.command.sequencer.state;

import com.github.kwoin.kgate.core.sequencing.state.ReadNBytesState;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.SmtpResponseSequencer;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.callback.ReadStatusCodeCallback;


/**
 * @author P. WILLEMET
 */
public class ReadStatusCodeState extends ReadNBytesState<SmtpResponseSequencer> {


    public ReadStatusCodeState(SmtpResponseSequencer stateMachine) {

        super(stateMachine,
                4,
                new ReadStatusCodeCallback(),
                true);

    }
}
