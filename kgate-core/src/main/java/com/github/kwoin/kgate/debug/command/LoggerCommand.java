package com.github.kwoin.kgate.debug.command;

import com.github.kwoin.kgate.core.command.ICommand;
import com.github.kwoin.kgate.core.command.chain.Chain;
import com.github.kwoin.kgate.core.message.Message;
import com.github.kwoin.kgate.core.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public class LoggerCommand<T extends Message> implements ICommand<T> {


    private final Logger logger = LoggerFactory.getLogger(LoggerCommand.class);


    @Override
    public void execute(Session<T> session, Chain<T> callingChain, T message) throws IOException {

        logger.debug("NEW " + message.getClass().getSimpleName() + "\n" + new String(message.getToBeTransmitted()));

    }
}
