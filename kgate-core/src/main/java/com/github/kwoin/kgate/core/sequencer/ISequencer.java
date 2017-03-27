package com.github.kwoin.kgate.core.sequencer;

import com.github.kwoin.kgate.core.context.IContext;


/**
 * @author P. WILLEMET
 */
public interface ISequencer {

    void start(IContext context);

    ESequencerResult push(byte b);

    void reset();

}
