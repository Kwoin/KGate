package com.github.kwoin.kgate.core.command.chain;

import com.github.kwoin.kgate.core.message.Message;


/**
 * @author P. WILLEMET
 */
public interface IChainFactory<T extends Message> {


    DefaultChain<T> newChain();


}
