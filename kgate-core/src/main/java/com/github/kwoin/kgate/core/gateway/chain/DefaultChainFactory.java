package com.github.kwoin.kgate.core.gateway.chain;

import com.github.kwoin.kgate.core.context.IContext;


/**
 * @author P. WILLEMET
 */
public class DefaultChainFactory implements IChainFactory {


    @Override
    public IChain newChain(IContext context) {

        return new DefaultChain();

    }
}
