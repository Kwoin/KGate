package com.github.kwoin.kgate.core.gateway.server;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.processor.IProcessorFactory;
import com.github.kwoin.kgate.core.processor.chain.IChainFactory;

import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public interface IServer {


    void start(IContext context) throws KGateServerException;

    void stop() throws KGateServerException;

    void setProcessorFactory(IProcessorFactory processorFactory);

    void setSourceToTargetChainFactory(IChainFactory sourceToTargetChainFactory);

    void setTargetToSourceChainFactory(IChainFactory targetToSourceChainFactory);

    void onNewConnexion(Socket source);


}
