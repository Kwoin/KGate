package com.github.kwoin.kgate.smtp.sequencing.callback;

import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.core.sequencing.state.callback.IStateCallback;
import com.github.kwoin.kgate.smtp.sequencing.SmtpResponseSequencer;


/**
 * @author P. WILLEMET
 */
public class ReadReasonPhraseMultilinesCallback implements IStateCallback<SmtpResponseSequencer> {


    @Override
    public int run(byte[] dataRead, SmtpResponseSequencer stateMachine, AbstractState callingState) {

        callingState.reset();

        String reasonPhrase = new String(dataRead, 0, dataRead.length - 2);
        stateMachine.getSmtpMessage().setReasonPhrase(stateMachine.getSmtpMessage().getReasonPhrase() + reasonPhrase);

        return SmtpResponseSequencer.READ_STATUS_CODE;

    }
}
