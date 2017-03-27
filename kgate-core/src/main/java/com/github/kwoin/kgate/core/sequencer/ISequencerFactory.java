package com.github.kwoin.kgate.core.sequencer;

import com.github.kwoin.kgate.core.context.IContext;


/**
 * @author P. WILLEMET
 */
public interface ISequencerFactory {


    ISequencer newSequencer(IContext context);


}
