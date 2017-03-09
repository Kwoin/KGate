package com.github.kwoin.kgate.core.processor;

/**
 * @author P. WILLEMET
 */
public class DefaultProcessorFactory implements IProcessorFactory {


    @Override
    public IProcessor newProcessor() {

        return new DefaultProcessor();

    }

}
