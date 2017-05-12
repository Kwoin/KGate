package com.github.kwoin.kgate.core.transmitter;

import com.github.kwoin.kgate.core.command.ICommand;
import com.github.kwoin.kgate.core.command.chain.Chain;
import com.github.kwoin.kgate.core.message.Message;
import com.github.kwoin.kgate.core.session.Session;

import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public class Transmitter<T extends Message> implements ICommand<T> {


    @Override
    public void execute(Session<T> session, Chain<T> callingChain, T message) throws IOException {

        session.getOutput().getOutputStream().write(message.getToBeTransmitted());

    }


}
