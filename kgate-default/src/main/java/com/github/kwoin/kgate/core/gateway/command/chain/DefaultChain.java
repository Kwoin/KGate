package com.github.kwoin.kgate.core.gateway.command.chain;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.command.ICommand;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public class DefaultChain implements IChain {


    protected List<ICommand> commands;
    protected boolean interrupt;


    public DefaultChain(ICommand... commands) {

        this.commands = new ArrayList<>(Arrays.asList(commands));
        interrupt = false;

    }


    @Override
    public List<ICommand> getCommands() {

        return commands;

    }


    @Override
    public void run(IoPoint inputSource, IoPoint outputSource, IContext context, IChain callingChain) throws Exception {

        int k=0;
        while(k < commands.size() && !interrupt) {
            commands.get(k).run(inputSource, outputSource, context, this);
            inputSource.getInputStream().reset();
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
