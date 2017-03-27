package com.github.kwoin.kgate.core.gateway.command;

import com.github.kwoin.kgate.core.context.IContext;

import java.util.List;


/**
 * @author P. WILLEMET
 */
public interface ICommandListFactory {


    List<ICommand> newCommandList(IContext context);


}
