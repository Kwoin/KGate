package com.github.kwoin.kgate.core.command;

import com.github.kwoin.kgate.core.command.chain.DefaultChain;
import com.github.kwoin.kgate.core.message.Message;

import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public interface ICommand<T extends Message> {


    void execute(Socket input,
                 Socket output,
                 DefaultChain<T> callingChain,
                 T message);


}
