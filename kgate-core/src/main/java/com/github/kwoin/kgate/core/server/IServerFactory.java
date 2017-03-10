package com.github.kwoin.kgate.core.server;

import com.github.kwoin.kgate.core.context.IContext;


/**
 * @author P. WILLEMET
 */
public interface IServerFactory {


    IServer newServer(IContext context);


}
