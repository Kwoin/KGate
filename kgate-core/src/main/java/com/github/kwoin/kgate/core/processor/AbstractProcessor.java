package com.github.kwoin.kgate.core.processor;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.processor.chain.IChain;

import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public abstract class AbstractProcessor implements IProcessor {


    protected IChain sourceToTargetChain;
    protected IChain targetToSourceChain;


    public AbstractProcessor() {

        sourceToTargetChain = initializeSourceToTargetChain();
        targetToSourceChain = initializeTargetToSourceChain();

    }


    @Override
    public IChain getSourceToTargetChain() {

        return sourceToTargetChain;

    }


    @Override
    public IChain getTargetToSourceChain() {

        return targetToSourceChain;

    }


    @Override
    public void process(Socket source, Socket client, IContext context) {

        sourceToTargetChain.run(source, client, context);
        targetToSourceChain.run(source, client, context);

    }


    protected abstract IChain initializeSourceToTargetChain();


    protected abstract IChain initializeTargetToSourceChain();


}
