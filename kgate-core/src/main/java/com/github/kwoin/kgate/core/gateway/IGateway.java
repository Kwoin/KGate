package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.factory.IFactoryComponent;


/**
 * @author P. WILLEMET
 */
public interface IGateway extends IFactoryComponent {

    IContext getContext();

    void start() throws KGateServerException;

    void stop() throws KGateServerException;

}
