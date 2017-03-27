package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.context.DefaultContext;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.gateway.io.InputPointManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author P. WILLEMET
 */
public class DefaultGateway implements IGateway {


    private final Logger logger = LoggerFactory.getLogger(DefaultGateway.class);
    protected boolean started;
    protected IGatewayComponentsFactory kgateComponentFactory;
    protected InputPointManager inputPointManager;
    protected IContext context;


    public DefaultGateway(IGatewayComponentsFactory kgateComponentFactory) {

        started = false;
        context = new DefaultContext(IContext.ECoreScope.APPLICATION);
        this.kgateComponentFactory = kgateComponentFactory;

    }


    @Override
    public IContext getContext() {

        return context;

    }


    @Override
    public void start() throws KGateServerException {

        logger.debug("Starting Gateway (" + this + ") ...");

        inputPointManager = kgateComponentFactory.newInputPointManager(context);
        inputPointManager.start(context);
        started = true;

        logger.debug("Gateway (" + this + ") STARTED");

    }


    @Override
    public void stop() throws KGateServerException {

        logger.debug("Stopping Gateway...");

        inputPointManager.stop();
        inputPointManager = null;
        started = false;

        logger.debug("Gateway STOPPED");

    }

}
