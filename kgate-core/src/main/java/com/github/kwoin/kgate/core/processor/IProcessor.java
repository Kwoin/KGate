package com.github.kwoin.kgate.core.processor;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.processor.chain.IChain;

import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public interface IProcessor {

    IChain getSourceToTargetChain();

    IChain getTargetToSourceChain();

    void process(Socket source, Socket client, IContext context);

}
