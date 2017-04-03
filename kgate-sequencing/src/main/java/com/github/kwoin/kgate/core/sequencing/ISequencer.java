package com.github.kwoin.kgate.core.sequencing;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;


/**
 * @author P. WILLEMET
 */
public interface ISequencer {

    void init(IContext context, IoPoint inputPoint);

    void reset();

    IContext getContext();

    IoPoint getInputPoint();

    ESequencerResult push(byte b);

}
