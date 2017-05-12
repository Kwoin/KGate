package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.client.DefaultClientFactory;
import com.github.kwoin.kgate.core.client.IClientFactory;
import com.github.kwoin.kgate.core.command.ICommand;
import com.github.kwoin.kgate.core.command.ICommandFactory;
import com.github.kwoin.kgate.core.command.chain.Chain;
import com.github.kwoin.kgate.core.command.chain.DefaultChainFactory;
import com.github.kwoin.kgate.core.command.chain.IChainFactory;
import com.github.kwoin.kgate.core.configuration.KGateConfig;
import com.github.kwoin.kgate.core.message.Message;
import com.github.kwoin.kgate.core.sequencer.AbstractSequencer;
import com.github.kwoin.kgate.core.sequencer.ISequencerFactory;
import com.github.kwoin.kgate.core.server.DefaultServer;
import com.github.kwoin.kgate.core.server.IServer;
import com.github.kwoin.kgate.core.session.SessionManager;
import com.github.kwoin.kgate.core.transmitter.Transmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


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
    protected final List<ICommandFactory<L>> clientToServerCommandFactoryList = new ArrayList<>();
    protected final List<ICommandFactory<R>> serverToClientCommandFactoryList = new ArrayList<>();
    protected Transmitter<L> toServerTransmitter;
    protected Transmitter<R> toClientTransmitter;
    protected boolean started;


    public AbstractGateway() {

        server = new DefaultServer();
        clientFactory = new DefaultClientFactory();
        clientToServerChainFactory = new DefaultChainFactory<L>();
        serverToClientChainFactory = new DefaultChainFactory<R>();
        toServerTransmitter = new Transmitter<L>();
        toClientTransmitter = new Transmitter<R>();
        started = false;

    }


    public void start() {

        if(started) {
            logger.warn("Gateway already started!");
            return;
        }

        logger.debug("Starting Gateway (" + this + ") ...");

        server.start(left -> {

            logger.debug("New Connexion !");

            Socket right = clientFactory.newClient();
            AbstractSequencer<L> clientToServerSequencer = clientToServerSequencerFactory.newSequencer();
            AbstractSequencer<R> serverToClientSequencer = serverToClientSequencerFactory.newSequencer();

            List<ICommand<L>> leftCommands = new ArrayList<>();
            for (ICommandFactory<L> commandFactory : clientToServerCommandFactoryList)
                leftCommands.add(commandFactory.newCommand());
            leftCommands.add(toServerTransmitter);
            List<ICommand<R>> rightCommands = new ArrayList<>();
            for (ICommandFactory<R> commandFactory : serverToClientCommandFactoryList)
                rightCommands.add(commandFactory.newCommand());
            rightCommands.add(toClientTransmitter);

            Chain<L> clientToServerChain = clientToServerChainFactory.newChain(leftCommands);
            Chain<R> serverToClientChain = serverToClientChainFactory.newChain(rightCommands);

            SessionManager.getInstance().createSession(left,
                    right,
                    clientToServerSequencer,
                    clientToServerChain,
                    serverToClientSequencer,
                    serverToClientChain);

        });

        started = true;

        if(logger.isDebugEnabled())
            logger.debug("Gateway (" + this + ") STARTED" +
                    "\nlistening on " + KGateConfig.getConfig().getString("kgate.core.server.host") + ":" + KGateConfig.getConfig().getString("kgate.core.server.port") +
                    "\nforwarding to " + KGateConfig.getConfig().getString("kgate.core.client.host") + ":" + KGateConfig.getConfig().getString("kgate.core.client.port"));

    }


    public void stop() {

        if(!started) {
            logger.warn("Gateway not started!");
            return;
        }

        logger.debug("Stopping Gateway (" + this + ") ...");

        server.stop();
        SessionManager.getInstance().deleteAllSessions();
        started = false;

        logger.debug("Gateway (" + this + ") STOPPED");

    }


    public void addClientToServerCommandFactory(ICommandFactory<L> commandFactory) {

        clientToServerCommandFactoryList.add(commandFactory);

    }


    public void addServerToClientCommandFactory(ICommandFactory<R> commandFactory) {

        serverToClientCommandFactoryList.add(commandFactory);

    }

}
