package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;


/**
 * @author P. WILLEMET
 */
public interface IGateway {

    IContext getContext();

    void start() throws KGateServerException;

    void stop() throws KGateServerException;

}
