package com.github.kwoin.kgate.core.command;

import com.github.kwoin.kgate.core.command.chain.Chain;
import com.github.kwoin.kgate.core.context.Context;
import com.github.kwoin.kgate.core.message.Message;
import com.github.kwoin.kgate.core.sequencer.AbstractSequencer;
import com.github.kwoin.kgate.core.session.Session;

import java.io.IOException;
import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public abstract class FilterCommand<T extends Message> implements ICommand<T> {


    @Override
    public void execute(Session<T> session, Chain<T> callingChain, T message) throws IOException {

        Context sessionContext = session.getSessionContext();

        if(!accept(message, sessionContext)) {

            callingChain.interrupt();
            informSequencer(session.getSequencer(), sessionContext);
            informClient(session.getInput(), sessionContext);

        }

    }


    /**
     * Decide whether this message should be accepted or not
     * @param message
     * @param sessionContext
     * @return true is the message is allowed
     */
    protected abstract boolean accept(T message, Context sessionContext);


    /**
     * Inform the sequencer of the current session that the current message is discarded
     * @param sequencer
     * @param sessionContext
     */
    protected abstract void informSequencer(AbstractSequencer<T> sequencer, Context sessionContext);


    /**
     * Inform the client (from current session point of vue) that current message is discarded
     * @param client
     * @param sessionContext
     */
    protected abstract void informClient(Socket client, Context sessionContext);


}
