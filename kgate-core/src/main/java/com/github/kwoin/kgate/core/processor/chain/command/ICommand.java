package com.github.kwoin.kgate.core.processor.chain.command;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.processor.chain.IChain;

import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public interface ICommand {


    void run(Socket source, Socket client, IContext context, IChain chain);


}
