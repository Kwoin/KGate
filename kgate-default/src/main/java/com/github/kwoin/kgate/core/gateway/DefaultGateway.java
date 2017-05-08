package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.context.DefaultContext;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.factory.DefaultGatewayFactorySet;
import com.github.kwoin.kgate.core.factory.IGatewayFactorySet;
import com.github.kwoin.kgate.core.gateway.io.InputPointManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author P. WILLEMET
 */
public class DefaultGateway implements AbstractGateway {


    private final Logger logger = LoggerFactory.getLogger(DefaultGateway.class);
    protected boolean started;
    protected IGatewayFactorySet gatewayFactorySet;
    protected InputPointManager inputPointManager;
    protected IContext context;


    public DefaultGateway(IGatewayFactorySet gatewayFactorySet) {

        started = false;
        context = new DefaultContext(IContext.ECoreScope.APPLICATION);
        this.gatewayFactorySet = gatewayFactorySet;

    }


    public DefaultGateway() {

        this(new DefaultGatewayFactorySet());

    }


    @Override
    public IContext getContext() {

        return context;

    }


    @Override
    public void start() throws KGateServerException {

        logger.debug("Starting Gateway (" + this + ") ...");

        inputPointManager = gatewayFactorySet.getGatewayComponentsFactory().newInputPointManager(context);
        inputPointManager.setGatewayFactorySet(gatewayFactorySet);
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


    @Override
    public void setGatewayFactorySet(IGatewayFactorySet gatewayFactorySet) {

        this.gatewayFactorySet = gatewayFactorySet;

    }


    @Override
    public IGatewayFactorySet getGatewayFactorySet() {

        return gatewayFactorySet;

    }
}
