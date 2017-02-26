package com.github.kwoin.kgate.core.processor;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.processor.chain.DefaultChain;
import com.github.kwoin.kgate.core.processor.chain.IChain;
import com.github.kwoin.kgate.core.processor.chain.IChainFactory;

import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public class DefaultProcessor implements IProcessor {


    protected IChainFactory sourceToTargetChainFactory;
    protected IChainFactory targetToSourceChainFactory;


    public DefaultProcessor() {

        sourceToTargetChainFactory = new IChainFactory() {
            @Override
            public IChain newChain() {
                return new DefaultChain();
            }
        };

        targetToSourceChainFactory = new IChainFactory() {
            @Override
            public IChain newChain() {
                return new DefaultChain();
            }
        };

    }


    @Override
    public void setSourceToTargetChainFactory(IChainFactory sourceToTargetChainFactory) {

        this.sourceToTargetChainFactory = sourceToTargetChainFactory;

    }


    @Override
    public void setTargetToSourceChainFactory(IChainFactory targetToSourceChainFactory) {

        this.targetToSourceChainFactory = targetToSourceChainFactory;

    }


    @Override
    public void process(Socket source, Socket client, IContext context) {

        sourceToTargetChainFactory.newChain().run(source, client, context, null);
        targetToSourceChainFactory.newChain().run(client, source, context, null);

    }


}
