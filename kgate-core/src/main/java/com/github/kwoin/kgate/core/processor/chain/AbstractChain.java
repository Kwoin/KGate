package com.github.kwoin.kgate.core.processor.chain;

import com.github.kwoin.kgate.core.context.IContext;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * @author P. WILLEMET
 */
public abstract class AbstractChain implements IChain {


    protected Properties configuration;
    protected List<ICommand> commands;
    protected boolean interrupt;


    public AbstractChain() {

        commands = new ArrayList<>();
        interrupt = false;

    }


    @Override
    public List<ICommand> getCommands() {

        return commands;

    }


    @Override
    public void run(Socket source, Socket target, IContext context, IChain callingChain) {

        int k=0;
        while(k < commands.size() && !interrupt) {
            commands.get(k).run(source, target, context, this);
        }

        if(interrupt && callingChain != null)
            callingChain.interruptChain();

    }


    @Override
    public void interruptChain() {

        interrupt = true;

    }


    protected abstract List<ICommand> initializeChainCommands();

}
