package com.github.kwoin.kgate.core.socket;

import com.github.kwoin.kgate.core.stream.KGateInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketImpl;
import java.net.UnknownHostException;


/**
 * @author P. WILLEMET
 */
public class KGateSocket extends Socket {


    public KGateSocket() {

        super();

    }


    public KGateSocket(Proxy proxy) {

        super(proxy);

    }


    protected KGateSocket(SocketImpl impl) throws SocketException {

        super(impl);

    }


    public KGateSocket(String host, int port) throws UnknownHostException, IOException {

        super(host, port);

    }


    public KGateSocket(InetAddress address, int port) throws IOException {

        super(address, port);

    }


    public KGateSocket(String host, int port, InetAddress localAddr, int localPort) throws IOException {

        super(host, port, localAddr, localPort);

    }


    public KGateSocket(InetAddress address, int port, InetAddress localAddr, int localPort) throws IOException {

        super(address, port, localAddr, localPort);

    }


    @Override
    public InputStream getInputStream() throws IOException {

        InputStream in = super.getInputStream();
        return new KGateInputStream(in);

    }



}
