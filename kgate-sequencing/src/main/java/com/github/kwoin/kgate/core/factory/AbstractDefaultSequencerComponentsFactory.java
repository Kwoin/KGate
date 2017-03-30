package com.github.kwoin.kgate.core.factory;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.factory.ISequencerCommandComponentsFactory;
import com.github.kwoin.kgate.core.gateway.command.chain.IChain;
import com.github.kwoin.kgate.core.sequencing.ISequencer;


/**
 * @author P. WILLEMET
 */
public abstract class AbstractDefaultSequencerComponentsFactory implements ISequencerCommandComponentsFactory {


    protected Class<? extends ISequencer> sequencerClass;


    public AbstractDefaultSequencerComponentsFactory(Class<? extends ISequencer> sequencerClass) {

        this.sequencerClass = sequencerClass;

    }


    @Override
    public ISequencer newSequencer(IContext context) {

        try {
            return sequencerClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public abstract IChain onNewMessage(IContext context);


    @Override
    public abstract IChain onUnhandledMessage(IContext context);


}
