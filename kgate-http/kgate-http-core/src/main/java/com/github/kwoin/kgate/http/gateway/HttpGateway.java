package com.github.kwoin.kgate.http.gateway;

import com.github.kwoin.kgate.core.gateway.DefaultGateway;
import com.github.kwoin.kgate.core.processor.chain.IChainFactory;
import com.github.kwoin.kgate.http.processor.chain.HttpSourceToTargetChainFactory;


/**
 * @author P. WILLEMET
 */
public class HttpGateway extends DefaultGateway {


    public HttpGateway() {

        super();
        sourceToTargetChainFactory = new HttpSourceToTargetChainFactory();

    }


    public void setHttpChainFactory(IChainFactory httpChainFactory) {

        ((HttpSourceToTargetChainFactory)sourceToTargetChainFactory).setHttpChainFactory(httpChainFactory);

    }


    public void setOnUnhandledChainFactory(IChainFactory onUnhandledChainFactory) {

        ((HttpSourceToTargetChainFactory)sourceToTargetChainFactory).setOnUnhandledChainFactory(onUnhandledChainFactory);

    }


    @Override
    public void setSourceToTargetChainFactory(IChainFactory sourceToTargetChainFactory) {

        if (!(sourceToTargetChainFactory instanceof HttpSourceToTargetChainFactory))
            throw new IllegalArgumentException("sourceToTargetChainFactory must be of type HttpSourceToTargetChainFactory");

        super.setSourceToTargetChainFactory(sourceToTargetChainFactory);

    }

}
