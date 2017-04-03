package com.github.kwoin.kgate.smtp.processor.command.sequencer.callback;

import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.core.sequencing.state.callback.IStateCallback;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.SmtpResponseSequencer;


/**
 * @author P. WILLEMET
 */
public class ReadReasonPhraseMonolineCallback implements IStateCallback<SmtpResponseSequencer> {


    @Override
    public int run(byte[] dataRead, SmtpResponseSequencer stateMachine, AbstractState callingState) {

        String reasonPhrase = new String(dataRead, 0, dataRead.length - 2);
        stateMachine.getSmtpMessage().setReasonPhrase(stateMachine.getSmtpMessage().getReasonPhrase() + reasonPhrase);

        return IStateMachine.CUT;

    }
}
