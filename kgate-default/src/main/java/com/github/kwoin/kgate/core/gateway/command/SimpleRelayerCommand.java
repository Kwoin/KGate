package com.github.kwoin.kgate.core.gateway.command;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.command.chain.IChain;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author P. WILLEMET
 */
public class SimpleRelayerCommand implements ICommand {


    private final Logger logger = LoggerFactory.getLogger(SimpleRelayerCommand.class);


    @Override
    public void run(IoPoint inputPoint, IoPoint outputPoint, IContext context, IChain callingChain) throws Exception {

        int i;
        while((i = inputPoint.getInputStream().read()) != -1)
            outputPoint.getOutputStream().write(i);

        if(context.getScope() == IContext.ECoreScope.SESSION) {
            inputPoint.close();
            outputPoint.close();
        }

    }


}
