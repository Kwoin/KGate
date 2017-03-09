package com.github.kwoin.kgate.core.gateway.server;

import com.github.kwoin.kgate.core.configuration.KGateConfig;
import com.github.kwoin.kgate.core.context.DefaultContext;
import com.github.kwoin.kgate.core.context.EDirection;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.gateway.client.DefaultClientSocketFactory;
import com.github.kwoin.kgate.core.gateway.client.IClientSocketFactory;
import com.github.kwoin.kgate.core.processor.DefaultProcessorFactory;
import com.github.kwoin.kgate.core.processor.IProcessor;
import com.github.kwoin.kgate.core.processor.IProcessorFactory;
import com.github.kwoin.kgate.core.socket.KGateSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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

    protected IServerSocketFactory serverSocketFactory;
    protected IClientSocketFactory clientSocketFactory;
    protected ServerSocket serverSocket;
    protected IProcessorFactory processorFactory;
    protected ExecutorService threadPool;
    protected boolean isStopped;


    public DefaultServer() {

        this(new DefaultProcessorFactory(),
                new DefaultServerSocketFactory(),
                new DefaultClientSocketFactory());

    }


    public DefaultServer(IProcessorFactory processorFactory, IServerSocketFactory serverSocketFactory, IClientSocketFactory clientSocketFactory) {

        this.processorFactory = processorFactory;
        this.serverSocketFactory = serverSocketFactory;
        this.clientSocketFactory = clientSocketFactory;

    }


    @Override
    public void start(IContext context) throws KGateServerException {

        logger.debug("Starting Server (" + this + ") ...");

        try {
            int poolSize = KGateConfig.getConfig().getInt("kgate.core.server.poolSize", 10);
            threadPool = Executors.newFixedThreadPool(poolSize);

            serverSocket = serverSocketFactory.newServerSocket();

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

        logger.debug("Server (" + this + ") STARTED");


    }


    @Override
    public void stop() throws KGateServerException {

        logger.debug("Stopping Server...");

        isStopped = true;
        try {
            serverSocket.close();
            threadPool.shutdownNow();
        } catch (IOException e) {
            logger.error("Error on closing server", e);
        }

        logger.debug("Server STOPPED");

    }


    @Override
    public void setProcessorFactory(IProcessorFactory processorFactory) {

        this.processorFactory = processorFactory;

    }


    @Override
    public void onNewConnexion(Socket source, IContext context) {

        logger.info("New connexion");

        threadPool.submit(new Runnable() {
            @Override
            public void run() {

                try {

                    KGateSocket kgateSocketSource = new KGateSocket(source);

                    KGateSocket kgateSocketClient = clientSocketFactory.newClientSocket();

                    IContext sessionContext = new DefaultContext(IContext.ECoreScope.SESSION, context);
                    sessionContext.setVariable(IContext.ECoreScope.SESSION, EDirection.DIRECTION_FIELD, EDirection.REQUEST);

                    IProcessor processor = processorFactory.newProcessor();
                    processor.process(kgateSocketSource, kgateSocketClient, sessionContext);

                } catch (IOException|NoSuchElementException e) {
                    logger.error("Cannot instantiate connexion", e);
                }
            }

        });


    }
}
