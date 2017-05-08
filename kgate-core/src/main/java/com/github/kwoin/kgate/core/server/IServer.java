package com.github.kwoin.kgate.core.server;

import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public interface IServer {


    void start(Callback onNewConnectionCallback);


    void stop();


    interface Callback {

        void execute(Socket socket);

    }


}
