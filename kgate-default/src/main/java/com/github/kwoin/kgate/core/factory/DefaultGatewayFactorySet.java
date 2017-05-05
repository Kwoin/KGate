package com.github.kwoin.kgate.core.factory;


/**
 * @author P. WILLEMET
 */
public class DefaultGatewayFactorySet implements IGatewayFactorySet {


    protected IGatewayComponentsFactory gatewayComponentsFactory;
    protected IInputPointManagerComponentsFactory inputPointManagerComponentsFactory;
    protected IProcessorComponentsFactory processorComponentsFactory;


    public DefaultGatewayFactorySet() {

        gatewayComponentsFactory = new DefaultGatewayComponentsFactory();
        inputPointManagerComponentsFactory = new DefaultInputPointManagerComponentFactory();
        processorComponentsFactory = new DefaultProcessorComponentsFactory();

    }


    @Override
    public void setGatewayComponentsFactory(IGatewayComponentsFactory gatewayComponentsFactory) {

        this.gatewayComponentsFactory = gatewayComponentsFactory;

    }


    @Override
    public IGatewayComponentsFactory getGatewayComponentsFactory() {

        return gatewayComponentsFactory;

    }


    @Override
    public void setInputPointManagerComponentsFactory(IInputPointManagerComponentsFactory inputPointManagerComponentsFactory) {

        this.inputPointManagerComponentsFactory = inputPointManagerComponentsFactory;

    }


    @Override
    public IInputPointManagerComponentsFactory getInputPointManagerComponentsFactory() {

        return inputPointManagerComponentsFactory;

    }


    @Override
    public void setProcessorComponentsFactory(IProcessorComponentsFactory processorComponentsFactory) {

        this.processorComponentsFactory = processorComponentsFactory;

    }


    @Override
    public IProcessorComponentsFactory getProcessorComponentsFactory() {

        return processorComponentsFactory;

    }
}
