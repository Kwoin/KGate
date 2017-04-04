package com.github.kwoin.kgate.smtp.sequencing;

import com.github.kwoin.kgate.core.sequencing.StateMachineSequencer;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.smtp.model.SmtpMessage;


/**
 * @author P. WILLEMET
 */
public abstract class SmtpMessageSequencer<T extends SmtpMessage> extends StateMachineSequencer {


    protected T smtpMessage;


    @Override
    public abstract AbstractState[] initializeStates();


    public T getSmtpMessage() {

        return smtpMessage;

    }
}
