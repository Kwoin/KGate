package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.command.ISequencerComponentsFactory;
import com.github.kwoin.kgate.core.gateway.command.SequencerCommand;
import com.github.kwoin.kgate.core.gateway.command.SimpleLoggerCommand;
import com.github.kwoin.kgate.core.gateway.command.SimpleRelayerCommand;
import com.github.kwoin.kgate.core.gateway.command.chain.DefaultChain;
import com.github.kwoin.kgate.core.gateway.command.chain.IChain;
import com.github.kwoin.kgate.core.sequencing.ISequencer;

import javax.annotation.Nullable;


/**
 * @author P. WILLEMET
 */
public class SequencerKGateComponentsFactory extends DefaultKGateComponentsFactory implements ISequencerComponentsFactory {


    protected Class<? extends ISequencer> requestSequencerClass;
    protected Class<? extends ISequencer> responseSequencerClass;


    public SequencerKGateComponentsFactory(
            @Nullable Class<? extends ISequencer> requestSequencerClass,
            @Nullable Class<? extends ISequencer> responseSequencerClass) {

        this.requestSequencerClass = requestSequencerClass;
        this.responseSequencerClass = responseSequencerClass;

    }


    @Override
    public IChain newRequestChain(IContext context) {

        try {
            return new DefaultChain(requestSequencerClass != null
                    ? new SequencerCommand(this, requestSequencerClass.newInstance())
                    : new SimpleRelayerCommand());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public IChain onNewMessage(IContext context) {

        try {
        return new DefaultChain(responseSequencerClass != null
                ? new SequencerCommand(this, responseSequencerClass.newInstance())
                : new SimpleRelayerCommand());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public IChain onUnhandledMessage(IContext context) {

        return new DefaultChain(new SimpleLoggerCommand("Unhandled Message!"), new SimpleRelayerCommand());

    }
}
