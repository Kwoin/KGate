package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.context.DefaultContext;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.server.DefaultServerFactory;
import com.github.kwoin.kgate.core.server.IServer;
import com.github.kwoin.kgate.core.server.IServerFactory;
import com.github.kwoin.kgate.core.socket.clientsocket.DefaultClientSocketFactory;
import com.github.kwoin.kgate.core.socket.clientsocket.IClientSocketFactory;
import com.github.kwoin.kgate.core.socket.serversocket.DefaultServerSocketFactory;
import com.github.kwoin.kgate.core.socket.serversocket.IServerSocketFactory;
import com.github.kwoin.kgate.core.processor.DefaultProcessorFactory;
import com.github.kwoin.kgate.core.processor.IProcessorFactory;
import com.github.kwoin.kgate.core.processor.chain.DefaultChainFactory;
import com.github.kwoin.kgate.core.processor.chain.IChainFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author P. WILLEMET
 */
public class DefaultGateway implements IGateway {


    private final Logger logger = LoggerFactory.getLogger(DefaultGateway.class);
    protected boolean started;
    protected IServerFactory serverFactory;
    protected IProcessorFactory processorFactory;
    protected IServerSocketFactory serverSocketFactory;
    protected IClientSocketFactory clientSocketFactory;
    protected IChainFactory sourceToTargetChainFactory;
    protected IChainFactory targetToSourceChainFactory;
    protected IServer server;
    protected IContext context;


    public DefaultGateway() {

        started = false;
        context = new DefaultContext(IContext.ECoreScope.APPLICATION);
        this.serverFactory = new DefaultServerFactory();
        this.processorFactory = new DefaultProcessorFactory();
        this.serverSocketFactory = new DefaultServerSocketFactory();
        this.clientSocketFactory = new DefaultClientSocketFactory();
        this.sourceToTargetChainFactory = new DefaultChainFactory();
        this.targetToSourceChainFactory = new DefaultChainFactory();

    }


    @Override
    public IContext getContext() {

        return context;

    }


    @Override
    public IServer getServer() {

        return server;

    }


    @Override
    public void start() throws KGateServerException {

        logger.debug("Starting Gateway (" + this + ") ...");

        server = serverFactory.newServer(context);
        server.setServerSocketFactory(serverSocketFactory);
        server.setClientSocketFactory(clientSocketFactory);
        server.setProcessorFactory(processorFactory);
        server.setSourceToTargetChainFactory(sourceToTargetChainFactory);
        server.setTargetToSourceChainFactory(targetToSourceChainFactory);
        server.start(context);
        started = true;

        logger.debug("Gateway (" + this + ") STARTED");

    }


    @Override
    public void stop() throws KGateServerException {

        logger.debug("Stopping Gateway...");

        server.stop();
        server = null;
        started = false;

        logger.debug("Gateway STOPPED");

    }


    @Override
    public void setServerFactory(IServerFactory serverFactory) {

        this.serverFactory = serverFactory;

    }


    @Override
    public void setServerSocketFactory(IServerSocketFactory serverSocketFactory) {

        this.serverSocketFactory = serverSocketFactory;

    }


    @Override
    public void setClientSocketFactory(IClientSocketFactory clientSocketFactory) {

        this.clientSocketFactory = clientSocketFactory;

    }


    @Override
    public void setProcessorFactory(IProcessorFactory processorFactory) {

        this.processorFactory = processorFactory;

    }


    @Override
    public void setSourceToTargetChainFactory(IChainFactory sourceToTargetChainFactory) {

        this.sourceToTargetChainFactory = sourceToTargetChainFactory;

    }


    @Override
    public void setTargetToSourceChainFactory(IChainFactory targetToSourceChainFactory) {

        this.targetToSourceChainFactory = targetToSourceChainFactory;

    }

}
