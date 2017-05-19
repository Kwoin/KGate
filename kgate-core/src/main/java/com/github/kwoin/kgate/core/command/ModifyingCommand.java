package com.github.kwoin.kgate.core.command;

import com.github.kwoin.kgate.core.command.chain.Chain;
import com.github.kwoin.kgate.core.context.Context;
import com.github.kwoin.kgate.core.message.Message;
import com.github.kwoin.kgate.core.session.Session;

import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public abstract class ModifyingCommand<T extends Message> implements ICommand<T> {


    @Override
    public void execute(Session<T> session, Chain<T> callingChain, T message) throws IOException {

        Context sessionContext = session.getSessionContext();
        modifyMessage(message, sessionContext);
        message.commit();

    }


    /**
     * Modify message
     * @param message
     * @param sessionContext
     */
    protected abstract void modifyMessage(T message, Context sessionContext);


}
