package com.github.kwoin.kgate.core.gateway.io;

import com.github.kwoin.kgate.core.configuration.KGateConfig;
import com.github.kwoin.kgate.core.context.DefaultContext;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.gateway.processor.IProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
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
public class DefaultInputPointManager implements InputPointManager {


    private final Logger logger = LoggerFactory.getLogger(DefaultInputPointManager.class);

    protected IInputPointManagerComponentsFactory inputPointManagerComponentsFactory;
    protected ServerSocket serverSocket;
    protected ExecutorService threadPool;
    protected boolean isStopped;


    public DefaultInputPointManager(IInputPointManagerComponentsFactory inputPointManagerComponentsFactory) {

        this.inputPointManagerComponentsFactory = inputPointManagerComponentsFactory;

    }


    @Override
    public void start(IContext context) throws KGateServerException {

        logger.debug("Starting Server (" + this + ") ...");

        serverSocket = null;
        try {
            int poolSize = KGateConfig.getConfig().getInt("kgate.core.inputPointManager.poolSize", 10);
            threadPool = Executors.newFixedThreadPool(poolSize);

            ServerSocketFactory serverSocketFactory = null;

            int serverPort = KGateConfig.getConfig().getInt("kgate.core.server.port", 7070);
            String serverHost = KGateConfig.getConfig().getString("kgate.core.server.host", "127.0.0.1");
            InetAddress serverHostInet = InetAddress.getByName(serverHost);
            if (Boolean.parseBoolean(KGateConfig.getConfig().getString("kgate.core.security.tlsEnabled"))) {
                System.setProperty("javax.net.ssl.keyStore", KGateConfig.getConfig().getString("kgate.core.security.keystore.path"));
                System.setProperty("javax.net.ssl.keyStorePassword", KGateConfig.getConfig().getString("kgate.core.security.keystore.password"));
                if(!KGateConfig.getConfig().getString("kgate.core.security.truststore.path").isEmpty())
                    System.setProperty("javax.net.ssl.trustStore", KGateConfig.getConfig().getString("kgate.core.security.truststore.path"));
                if(!KGateConfig.getConfig().getString("kgate.core.security.truststore.password").isEmpty())
                    System.setProperty("javax.net.ssl.trustStore", KGateConfig.getConfig().getString("kgate.core.security.truststore.password"));
                serverSocketFactory = SSLServerSocketFactory.getDefault();
            } else {
                serverSocketFactory = ServerSocketFactory.getDefault();
            }

            serverSocket = serverSocketFactory.createServerSocket(serverPort, 0, serverHostInet);

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
            logger.error("Error on closing io", e);
        }

        logger.debug("Server STOPPED");

    }


    protected void onNewConnexion(Socket source, IContext context) {

        logger.info("New connexion");

        threadPool.submit(new Runnable() {
            @Override
            public void run() {

                try {

                    IContext sessionContext = new DefaultContext(IContext.ECoreScope.SESSION, context);

                    IoPoint ioPoint = inputPointManagerComponentsFactory.newInputPoint(sessionContext, source);
                    IoPoint outputPoint = inputPointManagerComponentsFactory.newOutputPoint(sessionContext);

                    IProcessor processor = inputPointManagerComponentsFactory.newProcessor(sessionContext);
                    processor.process(ioPoint, outputPoint, sessionContext);

                } catch (IOException|NoSuchElementException e) {
                    logger.error("Cannot instantiate connexion", e);
                }
            }

        });


    }
}
