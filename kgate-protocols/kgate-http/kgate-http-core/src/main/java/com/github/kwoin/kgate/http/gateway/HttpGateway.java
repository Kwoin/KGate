package com.github.kwoin.kgate.http.gateway;

import com.github.kwoin.kgate.core.factory.DefaultRequestSequencerComponentsFactory;
import com.github.kwoin.kgate.core.factory.DefaultResponseSequencerComponentsFactory;
import com.github.kwoin.kgate.core.factory.DefaultSequencerGatewayFactorySet;
import com.github.kwoin.kgate.core.factory.ISequencerGatewayFactorySet;
import com.github.kwoin.kgate.core.gateway.DefaultSequencerGateway;
import com.github.kwoin.kgate.http.sequencing.HttpRequestSequencer;
import com.github.kwoin.kgate.http.sequencing.HttpResponseSequencer;


/**
 * @author P. WILLEMET
 */
public class HttpGateway extends DefaultSequencerGateway {


    public HttpGateway(ISequencerGatewayFactorySet gatewayFactorySet) {

        super(gatewayFactorySet);

    }


    public HttpGateway() {

        this(new DefaultSequencerGatewayFactorySet());
        getGatewayFactorySet().getProcessorComponentsFactory().setRequestSequencerCommandComponentsFactory(new DefaultRequestSequencerComponentsFactory(HttpRequestSequencer.class));
        getGatewayFactorySet().getProcessorComponentsFactory().setResponseSequencerCommandComponentsFactory(new DefaultResponseSequencerComponentsFactory(HttpResponseSequencer.class));

    }


}
