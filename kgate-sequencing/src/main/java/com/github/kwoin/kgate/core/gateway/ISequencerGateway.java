package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.factory.ISequencerGatewayFactorySet;


/**
 * @author P. WILLEMET
 */
public interface ISequencerGateway extends AbstractGateway {


    @Override
    ISequencerGatewayFactorySet getGatewayFactorySet();


}
