package com.github.kwoin.kgate.core.factory;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.io.InputPointManager;


/**
 * @author P. WILLEMET
 */
public interface IGatewayComponentsFactory {

    InputPointManager newInputPointManager(IContext context);

}
