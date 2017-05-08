package com.github.kwoin.kgate.core.factory;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.factory.IGatewayComponentsFactory;
import com.github.kwoin.kgate.core.gateway.io.AbstractServer;
import com.github.kwoin.kgate.core.gateway.io.InputPointManager;


/**
 * @author P. WILLEMET
 */
public class DefaultGatewayComponentsFactory implements IGatewayComponentsFactory {


    @Override
    public InputPointManager newInputPointManager(IContext context) {

        return new AbstractServer();

    }


}
