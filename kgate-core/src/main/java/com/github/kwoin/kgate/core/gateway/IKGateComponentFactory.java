package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.chain.IChainFactory;
import com.github.kwoin.kgate.core.gateway.input.IInputPointManager;
import com.github.kwoin.kgate.core.gateway.input.InputPoint;
import com.github.kwoin.kgate.core.gateway.processor.IProcessor;


/**
 * @author P. WILLEMET
 */
public interface IKGateComponentFactory {

    IInputPointManager newInputPointManager(IContext context);

    InputPoint newInputPoint(IContext context);

    IOutputPoint newOutputPoint(IContext context);

    IProcessor newProcessor(IContext context);

    IChainFactory newRequestChainFactory(IContext context);

    IChainFactory newResponseChainFactory(IContext context);


}
