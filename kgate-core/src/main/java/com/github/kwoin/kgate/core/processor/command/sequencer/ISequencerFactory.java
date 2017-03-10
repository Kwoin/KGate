package com.github.kwoin.kgate.core.processor.command.sequencer;

import com.github.kwoin.kgate.core.context.IContext;


/**
 * @author P. WILLEMET
 */
public interface ISequencerFactory {


    ISequencer newSequencer(IContext context);


}
