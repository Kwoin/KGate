package com.github.kwoin.kgate.core.factory;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;
import com.github.kwoin.kgate.core.gateway.processor.IProcessor;

import java.io.Closeable;
import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public interface IInputPointManagerComponentsFactory<T extends Closeable> {


    IoPoint newInputPoint(IContext context, T backStream);

    IoPoint newOutputPoint(IContext context) throws IOException;

    IProcessor newProcessor(IContext context);


}
