package com.github.kwoin.kgate.core.factory;


/**
 * @author P. WILLEMET
 */
public class DefaultSequencerGatewayFactorySet extends DefaultGatewayFactorySet implements ISequencerGatewayFactorySet {


    public DefaultSequencerGatewayFactorySet() {
        
        super();
        processorComponentsFactory = new DefaultSequencerProcessorComponentsFactory();
        
    }


    @Override
    public void setProcessorComponentsFactory(IProcessorComponentsFactory processorComponentsFactory) {

        if(!(processorComponentsFactory instanceof ISequencerProcessorComponentsFactory))
            throw new IllegalArgumentException("Not an instance of ISequencerProcessorComponentsFactory");

        this.processorComponentsFactory = processorComponentsFactory;

    }


    @Override
    public ISequencerProcessorComponentsFactory getProcessorComponentsFactory() {

        return (ISequencerProcessorComponentsFactory) processorComponentsFactory;

    }
    
}
