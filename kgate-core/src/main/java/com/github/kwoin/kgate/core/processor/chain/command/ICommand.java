package com.github.kwoin.kgate.core.processor.chain.command;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.processor.chain.IChain;
import com.github.kwoin.kgate.core.socket.KGateSocket;

import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public interface ICommand {


    void run(KGateSocket source, KGateSocket target, IContext context, IChain callingChain) throws IOException;


}
