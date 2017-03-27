package com.github.kwoin.kgate.core.processor.chain;

import com.github.kwoin.kgate.core.sequencer.ISequencerFactory;


/**
 * @author P. WILLEMET
 */
public interface ISequencerChainFactory extends IChainFactory {


    void setOnSeparatorChainFactory(IChainFactory onSeparatorChainFactory);

    void setOnUnhandledChainFactory(IChainFactory onUnhandledChainFactory);

    void setSequencerFactory(ISequencerFactory sequencerFactory);


}
