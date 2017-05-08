package com.github.kwoin.kgate.core.server;

import com.github.kwoin.kgate.core.configuration.KGateConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;


/**
 * @author P. WILLEMET
 */
public class DefaultServer implements IServer {


    private final Logger logger = LoggerFactory.getLogger(DefaultServer.class);

    protected ServerSocket serverSocket;
    protected boolean started;


    @Override
    public void start(Callback onNewConnectionCallback) {

        logger.debug("Starting Server (" + this + ") ...");

        serverSocket = null;
        try {

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

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        started = true;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (started)
                        onNewConnectionCallback.execute(serverSocket.accept());
                } catch (SocketException e) {
                    logger.debug("Server accept() interrupted");
                } catch (IOException e) {
                    logger.error("Unexpected error while listening to new connection", e);
                }
            }
        });
        t.start();

        logger.debug("Server (" + this + ") STARTED");

    }


    @Override
    public void stop() {

        started = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.error("Unexpected error while closin server", e);
        }

    }




}
