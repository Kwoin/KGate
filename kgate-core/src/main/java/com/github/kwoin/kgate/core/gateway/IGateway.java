package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.gateway.server.IServer;


/**
 * @author P. WILLEMET
 */
public interface IGateway {

    IContext getContext();

    IServer getServer();

    void setServer(IServer server);

    void start() throws KGateServerException;

    void stop() throws KGateServerException;

}
