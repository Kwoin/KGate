package com.github.kwoin.kgate.core.processor.chain;

import com.github.kwoin.kgate.core.context.IContext;

import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public interface ICommand {


    void run(Socket source, Socket target, IContext context, IChain callingChain);


}
