package com.github.kwoin.kgate.core.gateway.command;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.command.chain.IChain;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;


/**
 * @author P. WILLEMET
 */
public interface ICommand {


    void run(IoPoint inputPoint, IoPoint outputPoint, IContext context, IChain callingChain) throws Exception;


}
