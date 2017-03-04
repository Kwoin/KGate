package com.github.kwoin.kgate.http.processor.chain.command;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.processor.chain.DefaultChain;
import com.github.kwoin.kgate.core.processor.chain.IChain;
import com.github.kwoin.kgate.core.processor.chain.IChainFactory;
import com.github.kwoin.kgate.core.processor.chain.command.AbstractSequencerCommand;
import com.github.kwoin.kgate.core.processor.chain.command.ICommand;
import com.github.kwoin.kgate.core.processor.chain.command.ICommandListFactory;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ISequencer;
import com.github.kwoin.kgate.http.processor.chain.command.sequencer.HttpMessageStateMachineSequencer;

import java.util.List;


/**
 * @author P. WILLEMET
 */
public class HttpSequencerCommand extends AbstractSequencerCommand {


    public HttpSequencerCommand(List<ICommand> commands) {

        super(
                new ISequencerFactory() {
                    @Override
                    public ISequencer newSequencer(IContext context) {
                        return new HttpMessageStateMachineSequencer(context);
                    }
                },
                new IChainFactory() {
                    @Override
                    public IChain newChain() {
                        IChain chain = new DefaultChain();
                        chain.setCommandListFactory(new ICommandListFactory() {
                            @Override
                            public List<ICommand> newCommandList() {
                                commands.add(0, new HttpReadRequestMethodCommand());
                                return commands;
                            }
                        });
                        return chain;
                    }
                },
                new IChainFactory() {
                    @Override
                    public IChain newChain() {
                        return new DefaultChain();
                    }
                });

    }


}
