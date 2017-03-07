package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.context.DefaultContext;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.gateway.server.DefaultServer;
import com.github.kwoin.kgate.core.gateway.server.IServer;
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

        this(new DefaultServer());

    }


    public DefaultGateway(IServer server) {

        this.server = server;
        started = false;
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

        logger.debug("Starting Gateway (" + this + ") ...");

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
    public void setServer(IServer server) {

        if(started)
            logger.warn("Cannot reset server while running");
        else
            this.server = server;

    }

}
