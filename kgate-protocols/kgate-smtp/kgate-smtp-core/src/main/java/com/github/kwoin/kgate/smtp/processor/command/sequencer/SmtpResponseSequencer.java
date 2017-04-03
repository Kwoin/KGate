package com.github.kwoin.kgate.smtp.processor.command.sequencer;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.smtp.model.SmtpResponse;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.state.ReadReasonPhraseMonolineState;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.state.ReadReasonPhraseMultilinesState;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.state.ReadStatusCodeState;


/**
 * @author P. WILLEMET
 */
public class SmtpResponseSequencer extends SmtpMessageSequencer<SmtpResponse> {


    public static final int READ_STATUS_CODE = 0;
    public static final int READ_REASON_PHRASE_MULTILINES = 1;
    public static final int READ_REASON_PHRASE_MONOLINE = 2;


    @Override
    public AbstractState[] initializeStates() {

        return new AbstractState[] {
                new ReadStatusCodeState(this),
                new ReadReasonPhraseMultilinesState(this),
                new ReadReasonPhraseMonolineState(this)
        };

    }


    @Override
    public void init(IContext context, IoPoint inputPoint) {

        super.init(context, inputPoint);
        smtpMessage = new SmtpResponse();

    }


    @Override
    public void reset() {

        super.reset();
        smtpMessage = new SmtpResponse();

    }

}
