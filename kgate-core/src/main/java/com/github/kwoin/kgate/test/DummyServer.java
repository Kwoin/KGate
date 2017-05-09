package com.github.kwoin.kgate.test;

import com.github.kwoin.kgate.core.configuration.KGateConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


/**
 * @author P. WILLEMET
 */
public class DummyServer {


    private final Logger logger = LoggerFactory.getLogger(DummyServer.class);
    private ServerSocket serverSocket;
    private boolean started;


    public void start(Callback callback, boolean tls) throws IOException {

        try {
            ServerSocketFactory factory = tls ? SSLServerSocketFactory.getDefault() : ServerSocketFactory.getDefault();
            serverSocket = factory.createServerSocket(
                    KGateConfig.getConfig().getInt("kgate.core.client.port"),
                    1,
                    InetAddress.getByName(KGateConfig.getConfig().getString("kgate.core.client.host")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        started = true;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (started)
                        callback.execute(serverSocket.accept());
                } catch (SocketException e) {
                    logger.debug("Server accept() interrupted");
                } catch (IOException e) {
                    logger.error("Unexpected error while listening to new connection", e);
                }
            }
        });
        t.start();

    }


    public void stop() throws IOException {

        started = false;
        serverSocket.close();

    }


    public interface Callback {

        void execute(Socket socket);

    }
}
