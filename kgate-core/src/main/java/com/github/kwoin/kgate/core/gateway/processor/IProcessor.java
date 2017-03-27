package com.github.kwoin.kgate.core.gateway.processor;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;


/**
 * @author P. WILLEMET
 */
public interface IProcessor {

    void process(IoPoint inputPoint, IoPoint outputPoint, IContext context);

}
