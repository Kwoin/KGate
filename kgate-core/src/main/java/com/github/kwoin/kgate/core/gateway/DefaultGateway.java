package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.context.DefaultContext;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.gateway.server.DefaultServer;
import com.github.kwoin.kgate.core.gateway.server.IServer;
import com.github.kwoin.kgate.core.processor.IProcessorFactory;
import com.github.kwoin.kgate.core.processor.chain.IChainFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author P. WILLEMET
 */
public class DefaultGateway implements IGateway {


    private final Logger logger = LoggerFactory.getLogger(DefaultGateway.class);
    protected boolean started;
    protected IServer server;
    protected IContext context;


    public DefaultGateway() {

        started = false;
        server = new DefaultServer();
        context = new DefaultContext(IContext.ECoreScope.APPLICATION);

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

        server.start(context);
        started = true;

    }


    @Override
    public void stop() throws KGateServerException {

        server.stop();
        server = null;
        started = false;

    }


    @Override
    public void setProcessorFactory(IProcessorFactory processorFactory) {

        server.setProcessorFactory(processorFactory);

    }


    @Override
    public void setSourceToTargetChainFactory(IChainFactory sourceToTargetChainFactory) {

        server.setSourceToTargetChainFactory(sourceToTargetChainFactory);

    }


    @Override
    public void setTargetToSourceChainFactory(IChainFactory targetToSourceChainFactory) {

        server.setTargetToSourceChainFactory(targetToSourceChainFactory);

    }

}
