package com.github.kwoin.kgate.core.gateway.socket.clientsocket;

import com.github.kwoin.kgate.core.gateway.socket.KGateSocket;

import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public interface IClientSocketFactory {


    KGateSocket newClientSocket() throws IOException;


}
