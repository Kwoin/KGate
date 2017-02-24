package com.github.kwoin.kgate.core.gateway.server;

import com.github.kwoin.kgate.core.configuration.KGateConfig;
import com.github.kwoin.kgate.core.context.DefaultContext;
import com.github.kwoin.kgate.core.context.EDirection;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.processor.IProcessorFactory;
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
    protected ExecutorService threadPool;
    protected boolean isStopped;
    protected IContext context;


    public DefaultServer(IProcessorFactory processorFactory) {

        this.processorFactory = processorFactory;

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
                        onNewConnexion(serverSocket.accept());
                    } catch (IOException e) {
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
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("Error on closing server", e);
        }

    }


    @Override
    public void setProcessorFactory(IProcessorFactory processorFactory) {

        this.processorFactory = processorFactory;

    }


    @Override
    public void onNewConnexion(Socket source) {

        logger.info("New connexion");

        threadPool.submit(new Runnable() {
            @Override
            public void run() {

                try {

                    KGateConfig.getConfig().setThrowExceptionOnMissing(true);
                    String host = KGateConfig.getConfig().getString("kgate.core.client.host");
                    KGateConfig.getConfig().setThrowExceptionOnMissing(false);
                    int port = KGateConfig.getConfig().getInt("kgate.core.client.port");
                    Socket client = new Socket(host, port);

                    IContext sessionContext = new DefaultContext(IContext.ECoreScope.SESSION, context);
                    sessionContext.setVariable(IContext.ECoreScope.SESSION, "direction", EDirection.REQUEST);

                    processorFactory.newProcessor().process(source, client, context);

                } catch (IOException|NoSuchElementException e) {
                    logger.error("Cannot instantiate connexion", e);
                }
            }

        });


    }
}
