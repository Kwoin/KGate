package com.github.kwoin.kgate.core.gateway.chain;

import com.github.kwoin.kgate.core.gateway.command.ICommand;
import com.github.kwoin.kgate.core.gateway.command.ICommandListFactory;

import java.util.List;


/**
 * @author P. WILLEMET
 */
public interface IChain extends ICommand {

    void setCommandListFactory(ICommandListFactory commandListFactory);

    List<ICommand> getCommands();

    void interruptChain();


}
