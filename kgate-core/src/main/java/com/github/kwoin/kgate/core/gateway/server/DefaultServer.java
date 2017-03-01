package com.github.kwoin.kgate.core.gateway.server;

import com.github.kwoin.kgate.core.configuration.KGateConfig;
import com.github.kwoin.kgate.core.context.DefaultContext;
import com.github.kwoin.kgate.core.context.EDirection;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.processor.DefaultProcessor;
import com.github.kwoin.kgate.core.processor.IProcessor;
import com.github.kwoin.kgate.core.processor.IProcessorFactory;
import com.github.kwoin.kgate.core.processor.chain.DefaultChain;
import com.github.kwoin.kgate.core.processor.chain.IChain;
import com.github.kwoin.kgate.core.processor.chain.IChainFactory;
import com.github.kwoin.kgate.core.socket.KGateSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author P. WILLEMET
 */
public class DefaultServer implements IServer {


    private final Logger logger = LoggerFactory.getLogger(DefaultServer.class);

    protected ServerSocket serverSocket;
    protected IProcessorFactory processorFactory;
    protected IChainFactory sourceToTargetChainFactory;
    protected IChainFactory targetToSourceChainFactory;
    protected ExecutorService threadPool;
    protected boolean isStopped;


    public DefaultServer() {

        processorFactory = new IProcessorFactory() {
            @Override
            public IProcessor newProcessor() {
                return new DefaultProcessor();
            }
        };

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
    public void start(IContext context) throws KGateServerException {

        if(processorFactory == null)
            throw new KGateServerException(new NullPointerException("processorFactory not set"));

        try {
            int poolSize = KGateConfig.getConfig().getInt("kgate.core.server.poolSize", 10);
            threadPool = Executors.newFixedThreadPool(poolSize);

            int serverPort = KGateConfig.getConfig().getInt("kgate.core.server.port", 7070);
            String serverHost = KGateConfig.getConfig().getString("kgate.core.server.host", "127.0.0.1");
            InetAddress serverHostInet = InetAddress.getByName(serverHost);
            serverSocket = new ServerSocket(serverPort, 0, serverHostInet);
        } catch (IOException|NoSuchElementException e) {
            throw new KGateServerException(e);
        }

        isStopped = false;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!isStopped) {

                    try {
                        onNewConnexion(serverSocket.accept(), context);
                    } catch (IOException e) {
                        if(!serverSocket.isClosed())
                            logger.error("Connexion failed", e);
                    }

                }
            }
        });

        t.start();


    }


    @Override
    public void stop() throws KGateServerException {

        isStopped = true;
        try {
            serverSocket.close();
            threadPool.shutdownNow();
        } catch (IOException e) {
            logger.error("Error on closing server", e);
        }

    }


    @Override
    public void setProcessorFactory(IProcessorFactory processorFactory) {

        this.processorFactory = processorFactory;

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
    public void onNewConnexion(Socket source, IContext context) {

        logger.info("New connexion");

        threadPool.submit(new Runnable() {
            @Override
            public void run() {

                try {

                    KGateSocket kgateSocketSource = new KGateSocket(source);

                    KGateConfig.getConfig().setThrowExceptionOnMissing(true);
                    String host = KGateConfig.getConfig().getString("kgate.core.client.host");
                    KGateConfig.getConfig().setThrowExceptionOnMissing(false);
                    int port = KGateConfig.getConfig().getInt("kgate.core.client.port");
                    KGateSocket kgateSocketClient = new KGateSocket(new Socket(host, port));

                    IContext sessionContext = new DefaultContext(IContext.ECoreScope.SESSION, context);
                    sessionContext.setVariable(IContext.ECoreScope.SESSION, "direction", EDirection.REQUEST);

                    IProcessor processor = processorFactory.newProcessor();
                    processor.setSourceToTargetChainFactory(sourceToTargetChainFactory);
                    processor.setTargetToSourceChainFactory(targetToSourceChainFactory);
                    processor.process(kgateSocketSource, kgateSocketClient, sessionContext);

                } catch (IOException|NoSuchElementException e) {
                    logger.error("Cannot instantiate connexion", e);
                }
            }

        });


    }
}
