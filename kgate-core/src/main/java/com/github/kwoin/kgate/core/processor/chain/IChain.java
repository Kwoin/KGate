package com.github.kwoin.kgate.core.processor.chain;

import com.github.kwoin.kgate.core.processor.command.ICommand;
import com.github.kwoin.kgate.core.processor.command.ICommandListFactory;

import java.util.List;


/**
 * @author P. WILLEMET
 */
public interface IChain extends ICommand {

    void setCommandListFactory(ICommandListFactory commandListFactory);

    List<ICommand> getCommands();

    void interruptChain();


}
