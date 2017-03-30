package com.github.kwoin.kgate.core.factory;

/**
 * @author P. WILLEMET
 */
public interface IFactoryComponent {


    void setGatewayFactorySet(IGatewayFactorySet gatewayFactorySet);

    IGatewayFactorySet getGatewayFactorySet();


}
