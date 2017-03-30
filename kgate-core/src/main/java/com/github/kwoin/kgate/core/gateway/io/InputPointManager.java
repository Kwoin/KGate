package com.github.kwoin.kgate.core.gateway.io;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.factory.IFactoryComponent;


/**
 * @author P. WILLEMET
 */
public interface InputPointManager extends IFactoryComponent {


    void start(IContext context) throws KGateServerException;

    void stop() throws KGateServerException;


}
