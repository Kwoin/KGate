package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.gateway.server.IServer;
import com.github.kwoin.kgate.core.processor.IProcessorFactory;
import com.github.kwoin.kgate.core.processor.chain.IChainFactory;


/**
 * @author P. WILLEMET
 */
public interface IGateway {

    IServer getServer();

    void start() throws KGateServerException;

    void stop() throws KGateServerException;

    void setProcessorFactory(IProcessorFactory processorFactory);

    void setSourceToTargetChainFactory(IChainFactory sourceToTargetChainFactory);

    void setTargetToSourceChainFactory(IChainFactory targetToSourceChainFactory);

}
