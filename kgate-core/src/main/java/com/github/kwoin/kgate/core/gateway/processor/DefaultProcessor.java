package com.github.kwoin.kgate.core.gateway.processor;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.chain.DefaultChainFactory;
import com.github.kwoin.kgate.core.gateway.chain.IChainFactory;
import com.github.kwoin.kgate.core.gateway.socket.KGateSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public class DefaultProcessor implements IProcessor {


    private final Logger logger = LoggerFactory.getLogger(DefaultProcessor.class);
    protected IChainFactory sourceToTargetChainFactory;
    protected IChainFactory targetToSourceChainFactory;


    public DefaultProcessor() {

        this.sourceToTargetChainFactory = new DefaultChainFactory();
        this.targetToSourceChainFactory = new DefaultChainFactory();

    }


    /*
    public DefaultProcessor(IChainFactory sourceToTargetChainFactory, IChainFactory targetToSourceChainFactory) {

        this.sourceToTargetChainFactory = sourceToTargetChainFactory;
        this.targetToSourceChainFactory = targetToSourceChainFactory;

    }*/


    @Override
    public void setSourceToTargetChainFactory(IChainFactory sourceToTargetChainFactory) {

        this.sourceToTargetChainFactory = sourceToTargetChainFactory;

    }


    @Override
    public void setTargetToSourceChainFactory(IChainFactory targetToSourceChainFactory) {

        this.targetToSourceChainFactory = targetToSourceChainFactory;

    }


    @Override
    public void process(KGateSocket source, KGateSocket client, IContext context) {

        logger.trace("process start");

        Thread sourceToTargetThread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    sourceToTargetChainFactory.newChain(context).run(source, client, context, null);
                } catch (IOException e) {
                    handleChainException(e, source, client);
                }
            }
        });

        Thread targetToSourceThread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    targetToSourceChainFactory.newChain(context).run(client, source, context, null);
                } catch (IOException e) {
                    handleChainException(e, source, client);
                }
            }
        });

        try {
            sourceToTargetThread.start();
            targetToSourceThread.start();

            sourceToTargetThread.join();
            targetToSourceThread.join();
        } catch (InterruptedException e) {
            logger.error("Processor interrupted", e);
        }

        logger.trace("process end");

    }


    protected void handleChainException(IOException e, Socket source, Socket client) {


        if(source.isClosed() && !client.isClosed()) {
            logger.info("Source closed, closing target...");
            try {
                client.close();
            } catch (IOException e1) {
                logger.error("Could not close target", e1);
            }
        } else if(client.isClosed() && !source.isClosed()) {
            logger.info("Target closed, closing source...");
            try {
                source.close();
            } catch (IOException e1) {
                logger.error("Could not close source", e1);
            }
        } else if(!client.isClosed() && !source.isClosed()){
            logger.error("Unexpected error", e);
            try  {
                client.close();
                source.close();
            } catch (IOException e1) {
                logger.error("Unexpected error", e);
            }
        }

    }


}
