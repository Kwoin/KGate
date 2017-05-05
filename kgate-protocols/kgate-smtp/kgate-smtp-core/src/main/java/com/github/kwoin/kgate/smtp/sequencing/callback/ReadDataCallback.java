package com.github.kwoin.kgate.smtp.sequencing.callback;

import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.core.sequencing.state.callback.IStateCallback;
import com.github.kwoin.kgate.smtp.sequencing.SmtpRequestSequencer;


/**
 * @author P. WILLEMET
 */
public class ReadDataCallback implements IStateCallback<SmtpRequestSequencer> {


    @Override
    public int run(byte[] dataRead, SmtpRequestSequencer stateMachine, AbstractState callingState) {

        String data = new String(dataRead, 0, dataRead.length - 5);
        stateMachine.getSmtpMessage().setParams(data);

        return IStateMachine.CUT;

    }
}
