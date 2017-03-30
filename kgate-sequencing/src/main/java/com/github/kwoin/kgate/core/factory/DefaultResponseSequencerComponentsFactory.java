package com.github.kwoin.kgate.core.factory;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.command.SimpleLoggerCommand;
import com.github.kwoin.kgate.core.gateway.command.SimpleRelayerCommand;
import com.github.kwoin.kgate.core.gateway.command.chain.DefaultChain;
import com.github.kwoin.kgate.core.gateway.command.chain.IChain;
import com.github.kwoin.kgate.core.sequencing.ISequencer;


/**
 * @author P. WILLEMET
 */
public class DefaultResponseSequencerComponentsFactory extends AbstractDefaultSequencerComponentsFactory {


    public DefaultResponseSequencerComponentsFactory(Class<? extends ISequencer> sequencerClass) {

        super(sequencerClass);

    }


    @Override
    public IChain onNewMessage(IContext context) {

        return new DefaultChain(new SimpleLoggerCommand("New Response!"), new SimpleRelayerCommand());

    }


    @Override
    public IChain onUnhandledMessage(IContext context) {

        return new DefaultChain(new SimpleLoggerCommand("Unhandled Request!"), new SimpleRelayerCommand());

    }
}
