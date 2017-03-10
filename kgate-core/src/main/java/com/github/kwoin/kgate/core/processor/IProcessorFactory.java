package com.github.kwoin.kgate.core.processor;

import com.github.kwoin.kgate.core.context.IContext;


/**
 * @author P. WILLEMET
 */
public interface IProcessorFactory {


    IProcessor newProcessor(IContext context);


}
