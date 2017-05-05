package com.github.kwoin.kgate.smtp.sequencing.callback;

import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.core.sequencing.state.callback.IStateCallback;
import com.github.kwoin.kgate.smtp.sequencing.SmtpResponseSequencer;


/**
 * @author P. WILLEMET
 */
public class ReadStatusCodeCallback implements IStateCallback<SmtpResponseSequencer> {


    private static final byte[] START_MAIL_REPLY_CODE = "354".getBytes();


    @Override
    public int run(byte[] dataRead, SmtpResponseSequencer stateMachine, AbstractState callingState) {

        String statusCode = new String(dataRead, 0, 3);
        stateMachine.getSmtpMessage().setStatusCode(statusCode);

        if(dataRead[3] == '-') {
            callingState.reset();
            return SmtpResponseSequencer.READ_REASON_PHRASE_MULTILINES;
        } else
            return SmtpResponseSequencer.READ_REASON_PHRASE_MONOLINE;

    }

}
