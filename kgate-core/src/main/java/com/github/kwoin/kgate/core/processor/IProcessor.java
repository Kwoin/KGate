package com.github.kwoin.kgate.core.processor;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.processor.chain.IChainFactory;

import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public interface IProcessor {

    void setSourceToTargetChainFactory(IChainFactory sourceToTargetChainFactory);

    void setTargetToSourceChainFactory(IChainFactory targetToSourceChainFactory);

    void process(Socket source, Socket client, IContext context);

}
