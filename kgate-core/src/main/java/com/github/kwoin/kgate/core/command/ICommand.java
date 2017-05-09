package com.github.kwoin.kgate.core.command;

import com.github.kwoin.kgate.core.command.chain.Chain;
import com.github.kwoin.kgate.core.message.Message;
import com.github.kwoin.kgate.core.session.Session;

import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public interface ICommand<T extends Message> {


    void execute(Session<T> session,
                 Chain<T> callingChain,
                 T message) throws IOException;


}
