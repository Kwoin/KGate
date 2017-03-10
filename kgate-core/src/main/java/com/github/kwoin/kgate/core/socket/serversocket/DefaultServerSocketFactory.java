package com.github.kwoin.kgate.core.socket.serversocket;

import com.github.kwoin.kgate.core.configuration.KGateConfig;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;


/**
 * @author P. WILLEMET
 */
public class DefaultServerSocketFactory implements IServerSocketFactory {


    @Override
    public ServerSocket newServerSocket() throws IOException {

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

        return serverSocketFactory.createServerSocket(serverPort, 0, serverHostInet);

    }

}
