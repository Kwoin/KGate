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
public class SimpleRelayer implements ICommand {


    private final Logger logger = LoggerFactory.getLogger(SimpleRelayer.class);


    @Override
    public void run(Socket source, Socket target, IContext context, IChain callingChain) {

        try {

            while(true)
                target.getOutputStream().write(source.getInputStream().read());

        } catch (IOException e) {

            if(source.isClosed()) {
                logger.info("Source closed, closing target...");
                try {
                    target.close();
                } catch (IOException e1) {
                    logger.error("Could not close target", e1);
                }
            } else if(target.isClosed()) {
                logger.info("Target closed, closing source...");
                try {
                    source.close();
                } catch (IOException e1) {
                    logger.error("Could not close source", e1);
                }
            } else {
                logger.error("Unexpected error", e);
                try  {
                    target.close();
                    source.close();
                } catch (IOException e1) {
                    logger.error("Unexpected error", e);
                }
            }

        }

    }


}
