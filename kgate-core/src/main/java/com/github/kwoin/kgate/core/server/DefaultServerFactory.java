package com.github.kwoin.kgate.core.server;

import com.github.kwoin.kgate.core.context.IContext;


/**
 * @author P. WILLEMET
 */
public class DefaultServerFactory implements IServerFactory {


    @Override
    public IServer newServer(IContext context) {

        return new DefaultServer();

    }

}
