package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.gateway.input.IInputPointManager;
import com.github.kwoin.kgate.core.gateway.input.IServerFactory;
import com.github.kwoin.kgate.core.gateway.socket.clientsocket.IClientSocketFactory;
import com.github.kwoin.kgate.core.gateway.socket.serversocket.IServerSocketFactory;
import com.github.kwoin.kgate.core.gateway.processor.IProcessorFactory;
import com.github.kwoin.kgate.core.gateway.chain.IChainFactory;


/**
 * @author P. WILLEMET
 */
public interface IGateway {

    IContext getContext();

    IInputPointManager getServer();

    void setServerFactory(IServerFactory serverFactory);

    void setServerSocketFactory(IServerSocketFactory serverSocketFactory);

    void setClientSocketFactory(IClientSocketFactory clientSocketFactory);

    void setProcessorFactory(IProcessorFactory processorFactory);

    void setSourceToTargetChainFactory(IChainFactory sourceToTargetChainFactory);

    void setTargetToSourceChainFactory(IChainFactory targetToSourceChainFactory);

    void start() throws KGateServerException;

    void stop() throws KGateServerException;

}
