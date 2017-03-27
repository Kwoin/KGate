package com.github.kwoin.kgate.core.gateway.processor;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.command.chain.IChain;


/**
 * @author P. WILLEMET
 */
public interface IProcessorComponentsFactory {


    IChain newRequestChain(IContext context);

    IChain newResponseChain(IContext context);


}
