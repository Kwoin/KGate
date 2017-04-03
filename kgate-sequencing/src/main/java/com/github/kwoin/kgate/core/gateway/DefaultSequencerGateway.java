package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.factory.DefaultSequencerGatewayFactorySet;
import com.github.kwoin.kgate.core.factory.IGatewayFactorySet;
import com.github.kwoin.kgate.core.factory.ISequencerGatewayFactorySet;


/**
 * @author P. WILLEMET
 */
public class DefaultSequencerGateway extends DefaultGateway implements ISequencerGateway {


    public DefaultSequencerGateway(ISequencerGatewayFactorySet gatewayFactorySet) {

        super(gatewayFactorySet);

    }


    public DefaultSequencerGateway() {

        this(new DefaultSequencerGatewayFactorySet());

    }


    @Override
    public ISequencerGatewayFactorySet getGatewayFactorySet() {

        return (ISequencerGatewayFactorySet) gatewayFactorySet;

    }


    @Override
    public void setGatewayFactorySet(IGatewayFactorySet gatewayFactorySet) {

        if(!(gatewayFactorySet instanceof ISequencerGatewayFactorySet))
            throw new IllegalArgumentException("Not an instance of ISequencerGatewayFactorySet");

        this.gatewayFactorySet = gatewayFactorySet;

    }
}
