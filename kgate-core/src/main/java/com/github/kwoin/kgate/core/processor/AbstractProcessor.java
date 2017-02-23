package com.github.kwoin.kgate.core.processor;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.processor.chain.IChain;

import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public abstract class AbstractProcessor implements IProcessor {


    @Override
    public void process(Socket source, Socket client, IContext context) {

        IChain chain = initializeChain();
        chain.run(source, client, context);

    }


    protected abstract IChain initializeChain();


}
