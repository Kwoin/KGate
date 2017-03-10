package com.github.kwoin.kgate.core.processor.chain;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.processor.command.DefaultCommandListFactory;
import com.github.kwoin.kgate.core.processor.command.ICommand;
import com.github.kwoin.kgate.core.processor.command.ICommandListFactory;
import com.github.kwoin.kgate.core.socket.KGateSocket;

import java.io.IOException;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public class DefaultChain implements IChain {


    protected ICommandListFactory commandListFactory;
    protected List<ICommand> commands;
    protected boolean interrupt;


    public DefaultChain() {

        this.commandListFactory = new DefaultCommandListFactory();
        interrupt = false;

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
    public void run(KGateSocket source, KGateSocket target, IContext context, IChain callingChain) throws IOException {

        commands = commandListFactory.newCommandList(context);

        int k=0;
        while(k < commands.size() && !interrupt) {
            commands.get(k).run(source, target, context, this);
            source.getInputStream().reset();
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
