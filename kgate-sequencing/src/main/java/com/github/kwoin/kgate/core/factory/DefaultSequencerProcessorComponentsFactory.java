package com.github.kwoin.kgate.core.factory;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.command.SequencerCommand;
import com.github.kwoin.kgate.core.gateway.command.SimpleRelayerCommand;
import com.github.kwoin.kgate.core.gateway.command.chain.DefaultChain;
import com.github.kwoin.kgate.core.gateway.command.chain.IChain;

import javax.annotation.Nullable;


/**
 * @author P. WILLEMET
 */
public class DefaultSequencerProcessorComponentsFactory implements ISequencerProcessorComponentsFactory {


    @Nullable private ISequencerCommandComponentsFactory requestSequencerCommandComponentsFactory;
    @Nullable private ISequencerCommandComponentsFactory responseSequencerCommandComponentsFactory;


    @Override
    public IChain newRequestChain(IContext context) {


        return new DefaultChain(requestSequencerCommandComponentsFactory != null
                ? new SequencerCommand(requestSequencerCommandComponentsFactory)
                : new SimpleRelayerCommand());

    }


    @Override
    public IChain newResponseChain(IContext context) {

        return new DefaultChain(responseSequencerCommandComponentsFactory != null
                ? new SequencerCommand(responseSequencerCommandComponentsFactory)
                : new SimpleRelayerCommand());

    }


    @Override
    public void setRequestSequencerCommandComponentsFactory(@Nullable ISequencerCommandComponentsFactory requestSequencerCommandComponentsFactory) {

        this.requestSequencerCommandComponentsFactory = requestSequencerCommandComponentsFactory;

    }


    @Override
    @Nullable
    public ISequencerCommandComponentsFactory getRequestSequencerCommandComponentsFactory() {

        return requestSequencerCommandComponentsFactory;

    }


    @Override
    public void setResponseSequencerCommandComponentsFactory(@Nullable ISequencerCommandComponentsFactory responseSequencerCommandComponentsFactory) {

        this.responseSequencerCommandComponentsFactory = responseSequencerCommandComponentsFactory;

    }


    @Override
    @Nullable
    public ISequencerCommandComponentsFactory getResponseSequencerCommandComponentsFactory() {

        return responseSequencerCommandComponentsFactory;

    }
}
