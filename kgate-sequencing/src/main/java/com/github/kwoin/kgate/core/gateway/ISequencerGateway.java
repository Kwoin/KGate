package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.factory.ISequencerGatewayFactorySet;


/**
 * @author P. WILLEMET
 */
public interface ISequencerGateway extends IGateway {


    @Override
    ISequencerGatewayFactorySet getGatewayFactorySet();


}
