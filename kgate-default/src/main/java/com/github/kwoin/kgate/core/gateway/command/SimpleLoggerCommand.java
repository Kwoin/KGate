package com.github.kwoin.kgate.core.gateway.command;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.command.chain.IChain;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author P. WILLEMET
 */
public class SimpleLoggerCommand implements ICommand {


    private final Logger logger = LoggerFactory.getLogger(SimpleLoggerCommand.class);
    private static final int BUFFER_SIZE = 1024;
    private String text;


    public SimpleLoggerCommand(String text) {

        this.text = text;

    }


    @Override
    public void run(IoPoint inputPoint, IoPoint outputPoint, IContext context, IChain callingChain) throws Exception {

        byte[] b = new byte[BUFFER_SIZE];
        int len;
        String str = "";
        while( (len = inputPoint.getInputStream().read(b)) > 0);
            str += new String(b);

        logger.info(text);
        logger.info(str);

    }


}
