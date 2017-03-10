package com.github.kwoin.kgate.core.socket.serversocket;

import java.io.IOException;
import java.net.ServerSocket;


/**
 * @author P. WILLEMET
 */
public interface IServerSocketFactory {


    ServerSocket newServerSocket() throws IOException;


}