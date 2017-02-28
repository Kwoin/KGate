package com.github.kwoin.kgate.core.processor;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.processor.chain.DefaultChain;
import com.github.kwoin.kgate.core.processor.chain.IChain;
import com.github.kwoin.kgate.core.processor.chain.IChainFactory;
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

        sourceToTargetChainFactory = new IChainFactory() {
            @Override
            public IChain newChain() {
                return new DefaultChain();
            }
        };

        targetToSourceChainFactory = new IChainFactory() {
            @Override
            public IChain newChain() {
                return new DefaultChain();
            }
        };

    }


    @Override
    public void setSourceToTargetChainFactory(IChainFactory sourceToTargetChainFactory) {

        this.sourceToTargetChainFactory = sourceToTargetChainFactory;

    }


    @Override
    public void setTargetToSourceChainFactory(IChainFactory targetToSourceChainFactory) {

        this.targetToSourceChainFactory = targetToSourceChainFactory;

    }


    @Override
    public void process(Socket source, Socket client, IContext context) {

        logger.trace("process start");

        try {

            sourceToTargetChainFactory.newChain().run(source, client, context, null);
            targetToSourceChainFactory.newChain().run(client, source, context, null);

        } catch (IOException e) {
            if(source.isClosed()) {
                logger.info("Source closed, closing target...");
                try {
                    client.close();
                } catch (IOException e1) {
                    logger.error("Could not close target", e1);
                }
            } else if(client.isClosed()) {
                logger.info("Target closed, closing source...");
                try {
                    source.close();
                } catch (IOException e1) {
                    logger.error("Could not close source", e1);
                }
            } else {
                logger.error("Unexpected error", e);
                try  {
                    client.close();
                    source.close();
                } catch (IOException e1) {
                    logger.error("Unexpected error", e);
                }
            }
        }

        logger.trace("process end");

    }


}
