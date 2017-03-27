package com.github.kwoin.kgate.core.gateway.chain;


import com.github.kwoin.kgate.core.context.IContext;


/**
 * @author P. WILLEMET
 */
public interface IChainFactory {


    IChain newChain(IContext context);


}
