package com.github.kwoin.kgate.core.factory;


/**
 * @author P. WILLEMET
 */
public interface IGatewayFactorySet {

    void setGatewayComponentsFactory(IGatewayComponentsFactory gatewayComponentsFactory);

    IGatewayComponentsFactory getGatewayComponentsFactory();

    void setInputPointManagerComponentsFactory(IInputPointManagerComponentsFactory inputPointManagerComponentsFactory);

    IInputPointManagerComponentsFactory getInputPointManagerComponentsFactory();

    void setProcessorComponentsFactory(IProcessorComponentsFactory processorComponentsFactory);

    IProcessorComponentsFactory getProcessorComponentsFactory();

}
