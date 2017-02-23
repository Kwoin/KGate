package com.github.kwoin.kgate.core.processor.chain;

import com.github.kwoin.kgate.core.processor.chain.command.ICommand;
import com.github.kwoin.kgate.core.context.IContext;

import java.net.Socket;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public interface IChain {


    List<ICommand> getCommands();

    void run(Socket source, Socket target, IContext context);

    void interruptChain();


}
