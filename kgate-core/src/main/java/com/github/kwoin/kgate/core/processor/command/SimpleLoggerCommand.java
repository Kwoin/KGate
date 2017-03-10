package com.github.kwoin.kgate.core.processor.command;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.processor.chain.IChain;
import com.github.kwoin.kgate.core.socket.KGateSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public class SimpleLoggerCommand implements ICommand {


    private final Logger logger = LoggerFactory.getLogger(SimpleLoggerCommand.class);
    private static final int BUFFER_SIZE = 1024;


    @Override
    public void run(KGateSocket source, KGateSocket target, IContext context, IChain callingChain) throws IOException {

        byte[] b = new byte[BUFFER_SIZE];
        int len;
        String str = "";
        while( (len = source.getInputStream().read(b)) > 0);
            str += new String(b);

        logger.info(str);

    }


}
