package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.command.ISequencerComponentsFactory;
import com.github.kwoin.kgate.core.gateway.command.SequencerCommand;
import com.github.kwoin.kgate.core.gateway.command.SimpleLoggerCommand;
import com.github.kwoin.kgate.core.gateway.command.SimpleRelayerCommand;
import com.github.kwoin.kgate.core.gateway.command.chain.DefaultChain;
import com.github.kwoin.kgate.core.gateway.command.chain.IChain;
import com.github.kwoin.kgate.sequencing.ISequencer;


/**
 * @author P. WILLEMET
 */
public class SequencerKGateComponentsFactory extends DefaultKGateComponentsFactory implements ISequencerComponentsFactory {


    protected Class<? extends ISequencer> sequencerClass;


    public SequencerKGateComponentsFactory(Class<? extends ISequencer> sequencerClass) {

        this.sequencerClass = sequencerClass;

    }


    @Override
    public IChain newRequestChain(IContext context) {

        try {
            return new DefaultChain(new SequencerCommand(this, sequencerClass.newInstance()));
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public IChain onNewMessage(IContext context) {

        return new DefaultChain(new SimpleRelayerCommand());

    }


    @Override
    public IChain onUnhandledMessage(IContext context) {

        return new DefaultChain(new SimpleLoggerCommand(), new SimpleRelayerCommand());

    }
}
