package com.github.kwoin.kgate.core.command.chain;

import com.github.kwoin.kgate.core.command.ICommand;
import com.github.kwoin.kgate.core.message.Message;


/**
 * @author P. WILLEMET
 */
public class DefaultChainFactory<T extends Message> implements IChainFactory<T> {


    @Override
    public Chain<T> newChain(Iterable<ICommand<T>> commands) {

        return new Chain<>(commands);

    }
}
