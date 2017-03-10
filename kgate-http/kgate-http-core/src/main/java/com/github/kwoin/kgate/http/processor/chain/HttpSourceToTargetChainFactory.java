package com.github.kwoin.kgate.http.processor.chain;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.processor.chain.DefaultChain;
import com.github.kwoin.kgate.core.processor.chain.DefaultChainFactory;
import com.github.kwoin.kgate.core.processor.chain.IChain;
import com.github.kwoin.kgate.core.processor.chain.IChainFactory;
import com.github.kwoin.kgate.core.processor.chain.SequencerChain;
import com.github.kwoin.kgate.core.processor.command.ICommand;
import com.github.kwoin.kgate.core.processor.command.ICommandListFactory;
import com.github.kwoin.kgate.core.processor.command.SequencerCommand;
import com.github.kwoin.kgate.core.processor.command.SimpleRelayerCommand;
import com.github.kwoin.kgate.core.processor.command.sequencer.ISequencer;
import com.github.kwoin.kgate.core.processor.command.sequencer.ISequencerFactory;
import com.github.kwoin.kgate.http.processor.chain.command.HttpReadRequestMethodCommand;
import com.github.kwoin.kgate.http.processor.chain.command.sequencer.HttpMessageStateMachineSequencer;

import java.util.Arrays;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public class HttpSourceToTargetChainFactory implements IChainFactory {


    private ISequencerFactory sequencerFactory;
    private IChainFactory onSeparatorChainFactory;
    private IChainFactory httpChainFactory;
    private IChainFactory onUnhandledChainFactory;


    public HttpSourceToTargetChainFactory() {

        sequencerFactory = new ISequencerFactory() {
            @Override
            public ISequencer newSequencer(IContext context) {
                return new HttpMessageStateMachineSequencer(context);
            }
        };

        onSeparatorChainFactory = new IChainFactory() {
            @Override
            public IChain newChain(IContext context) {
                IChain chain = new DefaultChain();
                chain.setCommandListFactory(new ICommandListFactory() {
                    @Override
                    public List<ICommand> newCommandList(IContext context) {
                        return Arrays.asList(new HttpReadRequestMethodCommand(),
                                httpChainFactory.newChain(context),
                                new SimpleRelayerCommand());
                    }
                });
                return chain;
            }
        };

        onUnhandledChainFactory = new DefaultChainFactory();

    }


    public void setHttpChainFactory(IChainFactory httpChainFactory) {

        this.httpChainFactory = httpChainFactory;

    }


    public void setOnUnhandledChainFactory(IChainFactory onUnhandledChainFactory) {

        this.onUnhandledChainFactory = onUnhandledChainFactory;

    }


    @Override
    public IChain newChain(IContext context) {

        return new SequencerChain(new SequencerCommand(sequencerFactory, onSeparatorChainFactory, onUnhandledChainFactory));

    }
}
