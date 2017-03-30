package com.github.kwoin.kgate.core.gateway.processor;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.factory.IGatewayFactorySet;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public class DefaultProcessor implements IProcessor {


    private final Logger logger = LoggerFactory.getLogger(DefaultProcessor.class);
    protected IGatewayFactorySet gatewayFactorySet;


    @Override
    public void process(IoPoint inputPoint, IoPoint outputPoint, IContext context) {

        logger.trace("process start");

        Thread requestThread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    gatewayFactorySet.getProcessorComponentsFactory().newRequestChain(context).run(inputPoint, outputPoint, context, null);
                } catch (Exception e) {
                    handleChainException(e, inputPoint, outputPoint);
                }
            }
        });

        Thread responseThread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    gatewayFactorySet.getProcessorComponentsFactory().newResponseChain(context).run(outputPoint, inputPoint, context, null);
                } catch (Exception e) {
                    handleChainException(e, inputPoint, outputPoint);
                }
            }
        });

        try {
            requestThread.start();
            responseThread.start();

            requestThread.join();
            responseThread.join();
        } catch (InterruptedException e) {
            logger.error("Processor interrupted", e);
        }

        logger.trace("process end");

    }


    protected void handleChainException(Exception e, IoPoint inputPoint, IoPoint outputPoint) {

        try {
            inputPoint.close();
            logger.warn("InputPoint closed in response to exception (" + e + ")");
        } catch (IOException e1) {
            // ignore
        }
        try {
            outputPoint.close();
            logger.warn("OutputPoint closed in response to exception (" + e + ")");
        } catch (IOException e1) {
            // ignore
        }

    }


    @Override
    public void setGatewayFactorySet(IGatewayFactorySet gatewayFactorySet) {

        this.gatewayFactorySet = gatewayFactorySet;

    }


    @Override
    public IGatewayFactorySet getGatewayFactorySet() {

        return gatewayFactorySet;

    }
}
