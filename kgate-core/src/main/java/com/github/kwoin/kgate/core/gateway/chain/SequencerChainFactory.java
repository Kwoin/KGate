package com.github.kwoin.kgate.core.gateway.chain;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.sequencer.ISequencerFactory;


/**
 * @author P. WILLEMET
 */
public class SequencerChainFactory implements ISequencerChainFactory {


    private ISequencerFactory sequencerFactory;
    private IChainFactory onSeparatorChainFactory;
    private IChainFactory onUnhandledChainFactory;


    public SequencerChainFactory(ISequencerFactory sequencerFactory, IChainFactory onSeparatorChainFactory, IChainFactory onUnhandledChainFactory) {

        this.sequencerFactory = sequencerFactory;
        this.onSeparatorChainFactory = onSeparatorChainFactory;
        this.onUnhandledChainFactory = onUnhandledChainFactory;

    }


    @Override
    public IChain newChain(IContext context) {

        IChain chain = new DefaultChain();
        chain.setCommandListFactory(new );

    }


    @Override
    public void setOnSeparatorChainFactory(IChainFactory onSeparatorChainFactory) {

    }


    @Override
    public void setOnUnhandledChainFactory(IChainFactory onUnhandledChainFactory) {

    }
}
