package com.github.kwoin.kgate.core.processor.chain;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.processor.chain.command.ICommand;
import com.github.kwoin.kgate.core.processor.chain.command.ICommandListFactory;
import com.github.kwoin.kgate.core.processor.chain.command.SimpleRelayer;

import java.net.Socket;
import java.util.Arrays;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public class DefaultChain implements IChain {


    protected ICommandListFactory commandListFactory;
    protected List<ICommand> commands;
    protected boolean interrupt;


    public DefaultChain() {

        interrupt = false;
        commandListFactory = new ICommandListFactory() {
            @Override
            public List<ICommand> newCommandList() {
                return Arrays.asList(new SimpleRelayer());
            }
        };

    }


    @Override
    public void setCommandListFactory(ICommandListFactory commandListFactory) {

        this.commandListFactory = commandListFactory;

    }


    @Override
    public List<ICommand> getCommands() {

        return commands;

    }


    @Override
    public void run(Socket source, Socket target, IContext context, IChain callingChain) {

        commands = commandListFactory.newCommandList();

        int k=0;
        while(k < commands.size() && !interrupt) {
            commands.get(k).run(source, target, context, this);
            k++;
        }

        if(interrupt && callingChain != null)
            callingChain.interruptChain();

    }


    @Override
    public void interruptChain() {

        interrupt = true;

    }


}