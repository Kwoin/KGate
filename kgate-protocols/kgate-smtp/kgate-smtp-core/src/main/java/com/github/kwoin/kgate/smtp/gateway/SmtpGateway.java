package com.github.kwoin.kgate.smtp.gateway;

import com.github.kwoin.kgate.core.factory.DefaultRequestSequencerComponentsFactory;
import com.github.kwoin.kgate.core.factory.DefaultResponseSequencerComponentsFactory;
import com.github.kwoin.kgate.core.factory.DefaultSequencerGatewayFactorySet;
import com.github.kwoin.kgate.core.factory.ISequencerGatewayFactorySet;
import com.github.kwoin.kgate.core.gateway.DefaultSequencerGateway;
import com.github.kwoin.kgate.smtp.sequencing.SmtpRequestSequencer;
import com.github.kwoin.kgate.smtp.sequencing.SmtpResponseSequencer;


/**
 * @author P. WILLEMET
 */
public class SmtpGateway extends DefaultSequencerGateway {


    public SmtpGateway(ISequencerGatewayFactorySet gatewayFactorySet) {

        super(gatewayFactorySet);

    }


    public SmtpGateway() {

        this(new DefaultSequencerGatewayFactorySet());
        getGatewayFactorySet().getProcessorComponentsFactory().setRequestSequencerCommandComponentsFactory(new DefaultRequestSequencerComponentsFactory(SmtpRequestSequencer.class));
        getGatewayFactorySet().getProcessorComponentsFactory().setResponseSequencerCommandComponentsFactory(new DefaultResponseSequencerComponentsFactory(SmtpResponseSequencer.class));

    }


}
