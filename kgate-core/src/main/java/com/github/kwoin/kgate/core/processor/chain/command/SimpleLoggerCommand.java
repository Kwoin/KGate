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
public class SimpleLoggerCommand implements ICommand {


    private final Logger logger = LoggerFactory.getLogger(SimpleLoggerCommand.class);
    private static final int BUFFER_SIZE = 1024;


    @Override
    public void run(Socket source, Socket target, IContext context, IChain callingChain) throws IOException {

        byte[] b = new byte[BUFFER_SIZE];
        int len;
        String str = "";
        while( (len = source.getInputStream().read(b)) > 0);
            str += new String(b);

        logger.info(str);

    }


}
