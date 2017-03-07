package com.github.kwoin.kgate.http.gateway;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.DefaultGateway;
import com.github.kwoin.kgate.core.gateway.server.DefaultServer;
import com.github.kwoin.kgate.core.gateway.server.IServer;
import com.github.kwoin.kgate.core.processor.DefaultProcessor;
import com.github.kwoin.kgate.core.processor.IProcessor;
import com.github.kwoin.kgate.core.processor.IProcessorFactory;
import com.github.kwoin.kgate.core.processor.chain.DefaultChain;
import com.github.kwoin.kgate.core.processor.chain.IChain;
import com.github.kwoin.kgate.core.processor.chain.IChainFactory;
import com.github.kwoin.kgate.core.processor.chain.SequencerChain;
import com.github.kwoin.kgate.core.processor.chain.command.ICommand;
import com.github.kwoin.kgate.core.processor.chain.command.ICommandListFactory;
import com.github.kwoin.kgate.core.processor.chain.command.SequencerCommand;
import com.github.kwoin.kgate.core.processor.chain.command.SimpleRelayerCommand;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ISequencer;
import com.github.kwoin.kgate.http.processor.chain.command.HttpReadRequestMethodCommand;
import com.github.kwoin.kgate.http.processor.chain.command.sequencer.HttpMessageStateMachineSequencer;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public class HttpGateway extends DefaultGateway {


    protected ICommandListFactory httpCommandListFactory;


    public HttpGateway(@Nullable ICommandListFactory httpCommandListFactory) {

        super();
        setServer(createServer());

        this.httpCommandListFactory = httpCommandListFactory != null
                ? httpCommandListFactory
                : new ICommandListFactory() {
                    @Override
                    public List<ICommand> newCommandList() {
                        return Collections.EMPTY_LIST;
                    }
                };

    }


    protected IServer createServer() {

        return new DefaultServer(new IProcessorFactory() {
            @Override
            public IProcessor newProcessor() {
                return createProcessor();
            }
        });

    }


    protected IProcessor createProcessor() {

        return new DefaultProcessor(
                new IChainFactory() {
                    @Override
                    public IChain newChain() {
                        return createSourceToTargetChain();
                    }
                },
                new IChainFactory() {
                    @Override
                    public IChain newChain() {
                        return createTargetToSourceChain();
                    }
                });

    }


    protected IChain createSourceToTargetChain() {

        return new SequencerChain(new SequencerCommand(
                new SequencerCommand.ISequencerFactory() {
                    @Override
                    public ISequencer newSequencer(IContext context) {
                        return createSequencer(context);
                    }
                },
                new IChainFactory() {
                    @Override
                    public IChain newChain() {
                        return createOnSeparatorChain();
                    }
                },
                new IChainFactory() {
                    @Override
                    public IChain newChain() {
                        return createOnUnhandledChain();
                    }
                }

        ));

    }


    protected ISequencer createSequencer(IContext context) {

        return new HttpMessageStateMachineSequencer(context);

    }


    protected IChain createOnSeparatorChain() {

        return new DefaultChain(new ICommandListFactory() {
            @Override
            public List<ICommand> newCommandList() {

                return Arrays.asList(
                        new HttpReadRequestMethodCommand(),
                        new DefaultChain(httpCommandListFactory),
                        new SimpleRelayerCommand()
                );
            }
        });

    }


    protected IChain createOnUnhandledChain() {

        return new DefaultChain();

    }


    protected IChain createTargetToSourceChain() {

        return new DefaultChain();

    }


}
