package com.github.kwoin.kgate.core.socket.clientsocket;

import com.github.kwoin.kgate.core.socket.KGateSocket;

import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public interface IClientSocketFactory {


    KGateSocket newClientSocket() throws IOException;


}
