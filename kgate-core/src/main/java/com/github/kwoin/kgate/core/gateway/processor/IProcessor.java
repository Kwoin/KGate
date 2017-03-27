package com.github.kwoin.kgate.core.gateway.processor;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.chain.IChainFactory;
import com.github.kwoin.kgate.core.gateway.socket.KGateSocket;


/**
 * @author P. WILLEMET
 */
public interface IProcessor {

    void setSourceToTargetChainFactory(IChainFactory sourceToTargetChainFactory);

    void setTargetToSourceChainFactory(IChainFactory targetToSourceChainFactory);

    void process(KGateSocket source, KGateSocket client, IContext context);

}
