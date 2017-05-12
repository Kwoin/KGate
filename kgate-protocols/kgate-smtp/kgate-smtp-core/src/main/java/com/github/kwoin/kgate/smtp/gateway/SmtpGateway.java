package com.github.kwoin.kgate.smtp.gateway;

import com.github.kwoin.kgate.core.gateway.AbstractGateway;
import com.github.kwoin.kgate.smtp.message.SmtpRequest;
import com.github.kwoin.kgate.smtp.message.SmtpResponse;
import com.github.kwoin.kgate.smtp.sequencer.SmtpRequestSequencer;
import com.github.kwoin.kgate.smtp.sequencer.SmtpResponseSequencer;


/**
 * @author P. WILLEMET
 */
public class SmtpGateway extends AbstractGateway<SmtpRequest, SmtpResponse> {


    public SmtpGateway() {

        clientToServerSequencerFactory = SmtpRequestSequencer::new;
        serverToClientSequencerFactory = SmtpResponseSequencer::new;

    }


}
