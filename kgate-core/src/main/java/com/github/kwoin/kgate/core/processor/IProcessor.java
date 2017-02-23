package com.github.kwoin.kgate.core.processor;

import com.github.kwoin.kgate.core.context.IContext;

import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public interface IProcessor {

    void process(Socket source, Socket client, IContext context);

}
