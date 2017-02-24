package com.github.kwoin.kgate.core.processor.chain;

import java.util.List;


/**
 * @author P. WILLEMET
 */
public interface IChain extends ICommand {


    List<ICommand> getCommands();

    void interruptChain();


}
