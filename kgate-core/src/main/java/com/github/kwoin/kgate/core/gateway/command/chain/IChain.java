package com.github.kwoin.kgate.core.gateway.command.chain;

import com.github.kwoin.kgate.core.gateway.command.ICommand;

import java.util.List;


/**
 * @author P. WILLEMET
 */
public interface IChain extends ICommand {

    List<ICommand> getCommands();

    void interruptChain();


}
