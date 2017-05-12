package com.github.kwoin.kgate.core.command;

import com.github.kwoin.kgate.core.message.Message;


/**
 * @author P. WILLEMET
 */
public interface ICommandFactory<T extends Message> {


    ICommand<T> newCommand();


}
