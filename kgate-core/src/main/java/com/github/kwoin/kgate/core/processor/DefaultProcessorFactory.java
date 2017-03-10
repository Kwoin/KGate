package com.github.kwoin.kgate.core.processor;

import com.github.kwoin.kgate.core.context.IContext;


/**
 * @author P. WILLEMET
 */
public class DefaultProcessorFactory implements IProcessorFactory {


    @Override
    public IProcessor newProcessor(IContext context) {

        return new DefaultProcessor();

    }

}
