package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.client.IClientFactory;
import com.github.kwoin.kgate.core.server.IServer;


/**
 * @author P. WILLEMET
 */
public abstract class AbstractGateway<L, R> {


    protected IServer server;
    protected IClientFactory clientFactory;
    protected ISequencerFactory<L> clientToServerSequencerFactory;
    protected ISequencerFactory<R> serverToClientSequencerFactory;
    protected IChainFactory<L> clientToServerChainFactory;
    protected IChainFactory<R> serverToClientChainFactory;
    protected boolean started;


    public void start()


    public void stop()

}
