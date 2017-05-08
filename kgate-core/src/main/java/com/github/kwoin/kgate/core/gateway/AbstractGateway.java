package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.client.IClientFactory;
import com.github.kwoin.kgate.core.command.chain.DefaultChain;
import com.github.kwoin.kgate.core.command.chain.IChainFactory;
import com.github.kwoin.kgate.core.message.Message;
import com.github.kwoin.kgate.core.sequencer.AbstractSequencer;
import com.github.kwoin.kgate.core.sequencer.ISequencerFactory;
import com.github.kwoin.kgate.core.server.IServer;
import com.github.kwoin.kgate.core.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public abstract class AbstractGateway<L extends Message, R extends Message> {


    private final Logger logger = LoggerFactory.getLogger(AbstractGateway.class);
    protected IServer server;
    protected IClientFactory clientFactory;
    protected ISequencerFactory<L> clientToServerSequencerFactory;
    protected ISequencerFactory<R> serverToClientSequencerFactory;
    protected IChainFactory<L> clientToServerChainFactory;
    protected IChainFactory<R> serverToClientChainFactory;
    protected boolean started;


    public void start() {

        if(started) {
            logger.warn("Gateway already started!");
            return;
        }

        server.start(left -> {

            Socket right = clientFactory.newClient();
            AbstractSequencer<L> clientToServerSequencer = clientToServerSequencerFactory.newSequencer();
            AbstractSequencer<R> serverToClientSequencer = serverToClientSequencerFactory.newSequencer();
            DefaultChain<L> clientToServerChain = clientToServerChainFactory.newChain();
            DefaultChain<R> serverToClientChain = serverToClientChainFactory.newChain();

            SessionManager.getInstance().createSession(left, right, clientToServerSequencer, clientToServerChain);
            SessionManager.getInstance().createSession(right, left, serverToClientSequencer, serverToClientChain);

        });

        started = true;

    }


    public void stop() {

        if(!started) {
            logger.warn("Gateway not started!");
            return;
        }

        server.stop();
        SessionManager.getInstance().deleteAllSessions();

    }

}
