package com.github.kwoin.kgate.smtp.processor.command.sequencer;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.smtp.model.SmtpRequest;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.state.ReadCommandState;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.state.ReadCommandValueState;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.state.ReadDataState;


/**
 * @author P. WILLEMET
 */
public class SmtpRequestSequencer extends SmtpMessageSequencer<SmtpRequest> {


    public static final int READ_COMMAND = 0;
    public static final int READ_COMMAND_VALUE = 1;
    public static final int READ_DATA = 2;


    @Override
    public AbstractState[] initializeStates() {

        return new AbstractState[] {
                new ReadCommandState(this),
                new ReadCommandValueState(this),
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

        if(!(smtpMessage.getCommand().equals("DATA") && smtpMessage.getCommandValue().length() == 0)) {
            super.reset();
            smtpMessage = new SmtpRequest();
        }

    }

}
