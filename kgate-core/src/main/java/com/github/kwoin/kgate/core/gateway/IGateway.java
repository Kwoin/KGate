package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.server.IServer;
import com.github.kwoin.kgate.core.server.IServerFactory;
import com.github.kwoin.kgate.core.socket.clientsocket.IClientSocketFactory;
import com.github.kwoin.kgate.core.socket.serversocket.IServerSocketFactory;
import com.github.kwoin.kgate.core.processor.IProcessorFactory;
import com.github.kwoin.kgate.core.processor.chain.IChainFactory;


/**
 * @author P. WILLEMET
 */
public interface IGateway {

    IContext getContext();

    IServer getServer();

    void setServerFactory(IServerFactory serverFactory);

    void setServerSocketFactory(IServerSocketFactory serverSocketFactory);

    void setClientSocketFactory(IClientSocketFactory clientSocketFactory);

    void setProcessorFactory(IProcessorFactory processorFactory);

    void setSourceToTargetChainFactory(IChainFactory sourceToTargetChainFactory);

    void setTargetToSourceChainFactory(IChainFactory targetToSourceChainFactory);

    void start() throws KGateServerException;

    void stop() throws KGateServerException;

}
