package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.context.DefaultContext;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
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


    public DefaultGateway(IServer server) {

        this.server = server;
        started = false;

    }


    @Override
    public void setServer(IServer server) {

        if(started)
            logger.warn("Cannot reset server while server is running");
        else
            this.server = server;

    }


    @Override
    public void start() throws KGateServerException {

        server.start(new DefaultContext(IContext.ECoreScope.APPLICATION));
        started = true;

    }


    @Override
    public void stop() throws KGateServerException {

        server.stop();
        server = null;
        started = false;

    }


}
