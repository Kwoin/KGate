package com.github.kwoin.kgate.core.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketImpl;
import java.net.SocketImplFactory;


/**
 * @author P. WILLEMET
 */
public class KGateServerSocket extends ServerSocket {


    /**
     * Creates an unbound server socket.
     *
     * @throws IOException IO error when opening the socket.
     * @revised 1.4
     */
    public KGateServerSocket() throws IOException {

    }


    /**
     * Creates a server socket, bound to the specified port. A port number
     * of {@code 0} means that the port number is automatically
     * allocated, typically from an ephemeral port range. This port
     * number can then be retrieved by calling {@link #getLocalPort getLocalPort}.
     * <p>
     * The maximum queue length for incoming connection indications (a
     * request to connect) is set to {@code 50}. If a connection
     * indication arrives when the queue is full, the connection is refused.
     * <p>
     * If the application has specified a server socket factory, that
     * factory's {@code createSocketImpl} method is called to create
     * the actual socket implementation. Otherwise a "plain" socket is created.
     * <p>
     * If there is a security manager,
     * its {@code checkListen} method is called
     * with the {@code port} argument
     * as its argument to ensure the operation is allowed.
     * This could result in a SecurityException.
     *
     * @param port the port number, or {@code 0} to use a port
     *             number that is automatically allocated.
     * @throws IOException              if an I/O error occurs when opening the socket.
     * @throws SecurityException        if a security manager exists and its {@code checkListen}
     *                                  method doesn't allow the operation.
     * @throws IllegalArgumentException if the port parameter is outside
     *                                  the specified range of valid port values, which is between
     *                                  0 and 65535, inclusive.
     * @see SocketImpl
     * @see SocketImplFactory#createSocketImpl()
     * @see ServerSocket#setSocketFactory(SocketImplFactory)
     * @see SecurityManager#checkListen
     */
    public KGateServerSocket(int port) throws IOException {

        super(port);
    }


    /**
     * Creates a server socket and binds it to the specified local port
     * number, with the specified backlog.
     * A port number of {@code 0} means that the port number is
     * automatically allocated, typically from an ephemeral port range.
     * This port number can then be retrieved by calling
     * {@link #getLocalPort getLocalPort}.
     * <p>
     * The maximum queue length for incoming connection indications (a
     * request to connect) is set to the {@code backlog} parameter. If
     * a connection indication arrives when the queue is full, the
     * connection is refused.
     * <p>
     * If the application has specified a server socket factory, that
     * factory's {@code createSocketImpl} method is called to create
     * the actual socket implementation. Otherwise a "plain" socket is created.
     * <p>
     * If there is a security manager,
     * its {@code checkListen} method is called
     * with the {@code port} argument
     * as its argument to ensure the operation is allowed.
     * This could result in a SecurityException.
     * <p>
     * The {@code backlog} argument is the requested maximum number of
     * pending connections on the socket. Its exact semantics are implementation
     * specific. In particular, an implementation may impose a maximum length
     * or may choose to ignore the parameter altogther. The value provided
     * should be greater than {@code 0}. If it is less than or equal to
     * {@code 0}, then an implementation specific default will be used.
     * <p>
     *
     * @param port    the port number, or {@code 0} to use a port
     *                number that is automatically allocated.
     * @param backlog requested maximum length of the queue of incoming
     *                connections.
     * @throws IOException              if an I/O error occurs when opening the socket.
     * @throws SecurityException        if a security manager exists and its {@code checkListen}
     *                                  method doesn't allow the operation.
     * @throws IllegalArgumentException if the port parameter is outside
     *                                  the specified range of valid port values, which is between
     *                                  0 and 65535, inclusive.
     * @see SocketImpl
     * @see SocketImplFactory#createSocketImpl()
     * @see ServerSocket#setSocketFactory(SocketImplFactory)
     * @see SecurityManager#checkListen
     */
    public KGateServerSocket(int port, int backlog) throws IOException {

        super(port, backlog);

    }


    /**
     * Create a server with the specified port, listen backlog, and
     * local IP address to bind to.  The <i>bindAddr</i> argument
     * can be used on a multi-homed host for a ServerSocket that
     * will only accept connect requests to one of its addresses.
     * If <i>bindAddr</i> is null, it will default accepting
     * connections on any/all local addresses.
     * The port must be between 0 and 65535, inclusive.
     * A port number of {@code 0} means that the port number is
     * automatically allocated, typically from an ephemeral port range.
     * This port number can then be retrieved by calling
     * {@link #getLocalPort getLocalPort}.
     * <p>
     * <P>If there is a security manager, this method
     * calls its {@code checkListen} method
     * with the {@code port} argument
     * as its argument to ensure the operation is allowed.
     * This could result in a SecurityException.
     * <p>
     * The {@code backlog} argument is the requested maximum number of
     * pending connections on the socket. Its exact semantics are implementation
     * specific. In particular, an implementation may impose a maximum length
     * or may choose to ignore the parameter altogther. The value provided
     * should be greater than {@code 0}. If it is less than or equal to
     * {@code 0}, then an implementation specific default will be used.
     * <p>
     *
     * @param port     the port number, or {@code 0} to use a port
     *                 number that is automatically allocated.
     * @param backlog  requested maximum length of the queue of incoming
     *                 connections.
     * @param bindAddr the local InetAddress the server will bind to
     * @throws SecurityException        if a security manager exists and
     *                                  its {@code checkListen} method doesn't allow the operation.
     * @throws IOException              if an I/O error occurs when opening the socket.
     * @throws IllegalArgumentException if the port parameter is outside
     *                                  the specified range of valid port values, which is between
     *                                  0 and 65535, inclusive.
     * @see SocketOptions
     * @see SocketImpl
     * @see SecurityManager#checkListen
     * @since JDK1.1
     */
    public KGateServerSocket(int port, int backlog, InetAddress bindAddr) throws IOException {

        super(port, backlog, bindAddr);
    }
}
