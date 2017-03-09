package com.github.kwoin.kgate.core.gateway.client;

import com.github.kwoin.kgate.core.socket.KGateSocket;

import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public interface IClientSocketFactory {


    KGateSocket newClientSocket() throws IOException;


}
