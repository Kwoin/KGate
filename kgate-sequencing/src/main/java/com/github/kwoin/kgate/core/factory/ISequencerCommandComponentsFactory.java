package com.github.kwoin.kgate.core.factory;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.command.chain.IChain;
import com.github.kwoin.kgate.core.sequencing.ISequencer;


/**
 * @author P. WILLEMET
 */
public interface ISequencerCommandComponentsFactory {

    ISequencer newSequencer(IContext context);

    IChain onNewMessage(IContext context);

    IChain onUnhandledMessage(IContext context);


}
