package com.github.kwoin.kgate.core.factory;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.command.SimpleRelayerCommand;
import com.github.kwoin.kgate.core.gateway.command.chain.DefaultChain;
import com.github.kwoin.kgate.core.gateway.command.chain.IChain;


/**
 * @author P. WILLEMET
 */
public class DefaultProcessorComponentsFactory implements IProcessorComponentsFactory {


    @Override
    public IChain newRequestChain(IContext context) {

        return new DefaultChain(new SimpleRelayerCommand());

    }


    @Override
    public IChain newResponseChain(IContext context) {

        return new DefaultChain(new SimpleRelayerCommand());

    }
}
