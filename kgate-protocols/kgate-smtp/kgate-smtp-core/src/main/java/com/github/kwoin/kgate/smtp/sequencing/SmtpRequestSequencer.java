package com.github.kwoin.kgate.smtp.sequencing;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.smtp.model.SmtpRequest;
import com.github.kwoin.kgate.smtp.sequencing.state.ReadCommandState;
import com.github.kwoin.kgate.smtp.sequencing.state.ReadDataState;


/**
 * @author P. WILLEMET
 */
public class SmtpRequestSequencer extends SmtpMessageSequencer<SmtpRequest> {


    public static final int READ_COMMAND = 0;
    public static final int READ_DATA = 1;


    @Override
    public AbstractState[] initializeStates() {

        return new AbstractState[] {
                new ReadCommandState(this),
                new ReadDataState(this)
        };

    }


    @Override
    public void init(IContext context, IoPoint inputPoint) {

        super.init(context, inputPoint);

        smtpMessage = new SmtpRequest();

    }


    @Override
    public void reset() {

        if(!(smtpMessage.getCommand().equals("DATA") && smtpMessage.getParams().length() == 0)) {
            super.reset();
            smtpMessage = new SmtpRequest();
        }

    }

}
