package com.github.kwoin.kgate.core.server;

import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.processor.IProcessorFactory;

import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public interface IServer {


    void start() throws KGateServerException;

    void stop() throws KGateServerException;

    void setProcessorFactory(IProcessorFactory processorFactory);

    void onNewConnection(Socket source);


}
