package com.github.kwoin.kgate.core.gateway.command;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.command.chain.IChain;


/**
 * @author P. WILLEMET
 */
public interface ISequencerComponentsFactory {


    IChain onNewMessage(IContext context);

    IChain onUnhandledMessage(IContext context);


}
