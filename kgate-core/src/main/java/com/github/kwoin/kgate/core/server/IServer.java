package com.github.kwoin.kgate.core.server;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.socket.clientsocket.IClientSocketFactory;
import com.github.kwoin.kgate.core.socket.serversocket.IServerSocketFactory;
import com.github.kwoin.kgate.core.processor.IProcessorFactory;
import com.github.kwoin.kgate.core.processor.chain.IChainFactory;


/**
 * @author P. WILLEMET
 */
public interface IServer {


    void start(IContext context) throws KGateServerException;

    void stop() throws KGateServerException;

    void setProcessorFactory(IProcessorFactory processorFactory);

    void setServerSocketFactory(IServerSocketFactory serverSocketFactory);

    void setClientSocketFactory(IClientSocketFactory clientSocketFactory);

    void setSourceToTargetChainFactory(IChainFactory sourceToTargetChainFactory);

    void setTargetToSourceChainFactory(IChainFactory targetToSourceChainFactory);


}
