package com.github.kwoin.kgate.core.sequencing;

import com.github.kwoin.kgate.core.context.IContext;


/**
 * @author P. WILLEMET
 */
public interface ISequencer {

    void init(IContext context);

    ESequencerResult push(byte b);

}
