package com.github.kwoin.kgate.core.gateway.io;


import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;


/**
 * @author P. WILLEMET
 */
public class SocketPoint extends Socket implements IoPoint {


    private Socket socket;
    protected KGateInputStream in;


    public SocketPoint(Socket socket) {

        this.socket = socket;

    }


    public void connect(SocketAddress endpoint) throws IOException {
        socket.connect(endpoint);
    }


    public void connect(SocketAddress endpoint, int timeout) throws IOException {
        socket.connect(endpoint, timeout);
    }


    public void bind(SocketAddress bindpoint) throws IOException {
        socket.bind(bindpoint);
    }


    public InetAddress getInetAddress() {
        return socket.getInetAddress();
    }


    public InetAddress getLocalAddress() {
        return socket.getLocalAddress();
    }


    public int getPort() {
        return socket.getPort();
    }


    public int getLocalPort() {
        return socket.getLocalPort();
    }


    public SocketAddress getRemoteSocketAddress() {
        return socket.getRemoteSocketAddress();
    }


    public SocketAddress getLocalSocketAddress() {
        return socket.getLocalSocketAddress();
    }


    public SocketChannel getChannel() {
        return socket.getChannel();
    }


    public KGateInputStream getInputStream() throws IOException {

        if(in == null)
            in = new KGateInputStream(socket.getInputStream());

        return in;
    }


    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }


    public void setTcpNoDelay(boolean on) throws SocketException {
        socket.setTcpNoDelay(on);
    }


    public boolean getTcpNoDelay() throws SocketException {
        return socket.getTcpNoDelay();
    }


    public void setSoLinger(boolean on, int linger) throws SocketException {
        socket.setSoLinger(on, linger);
    }


    public int getSoLinger() throws SocketException {
        return socket.getSoLinger();
    }


    public void sendUrgentData (int data) throws IOException  {
        socket.sendUrgentData(data);
    }


    public void setOOBInline(boolean on) throws SocketException {
        socket.setOOBInline(on);
    }


    public boolean getOOBInline() throws SocketException {
        return socket.getOOBInline();
    }


    public synchronized void setSoTimeout(int timeout) throws SocketException {
        socket.setSoTimeout(timeout);
    }


    public synchronized int getSoTimeout() throws SocketException {
        return socket.getSoTimeout();
    }


    public synchronized void setSendBufferSize(int size)
            throws SocketException{
        socket.setSendBufferSize(size);
    }


    public synchronized int getSendBufferSize() throws SocketException {
        return socket.getSendBufferSize();
    }


    public synchronized void setReceiveBufferSize(int size)
            throws SocketException{
        socket.setReceiveBufferSize(size);
    }


    public synchronized int getReceiveBufferSize()
            throws SocketException{
        return socket.getReceiveBufferSize();
    }


    public void setKeepAlive(boolean on) throws SocketException {
        socket.setKeepAlive(on);
    }


    public boolean getKeepAlive() throws SocketException {
        return socket.getKeepAlive();
    }


    public void setTrafficClass(int tc) throws SocketException {
        socket.setTrafficClass(tc);
    }


    public int getTrafficClass() throws SocketException {
        return socket.getTrafficClass();
    }


    public void setReuseAddress(boolean on) throws SocketException {
        socket.setReuseAddress(on);
    }


    public boolean getReuseAddress() throws SocketException {
        return socket.getReuseAddress();
    }


    public synchronized void close() throws IOException {
        socket.close();
    }


    public void shutdownInput() throws IOException
    {
        socket.shutdownInput();
    }


    public void shutdownOutput() throws IOException
    {
        socket.shutdownOutput();
    }


    public String toString() {
        return socket.toString();
    }


    public boolean isConnected() {
        return socket.isConnected();
    }


    public boolean isBound() {
        return socket.isBound();
    }


    public boolean isClosed() {
        return socket.isClosed();
    }


    public boolean isInputShutdown() {
        return socket.isInputShutdown();
    }


    public boolean isOutputShutdown() {
        return socket.isOutputShutdown();
    }



    public void setPerformancePreferences(int connectionTime,
                                          int latency,
                                          int bandwidth)
    {
        socket.setPerformancePreferences(connectionTime, latency, bandwidth);
    }


}
