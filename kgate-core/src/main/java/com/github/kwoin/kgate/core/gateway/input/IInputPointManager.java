package com.github.kwoin.kgate.core.gateway.input;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;


/**
 * @author P. WILLEMET
 */
public interface IInputPointManager {


    void start(IContext context) throws KGateServerException;

    void stop() throws KGateServerException;


}
