package com.github.kwoin.kgate.core.processor.chain.command;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.processor.chain.IChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public class SimpleRelayerCommand implements ICommand {


    private final Logger logger = LoggerFactory.getLogger(SimpleRelayerCommand.class);


    @Override
    public void run(Socket source, Socket target, IContext context, IChain callingChain) throws IOException {

        int i;
        while((i = source.getInputStream().read()) != -1)
            target.getOutputStream().write(i);

        if(context.getScope() == IContext.ECoreScope.SESSION) {
            source.close();
            target.close();
        }

    }


}
