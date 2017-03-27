package com.github.kwoin.kgate.core.gateway.io;

import java.io.Closeable;
import java.io.OutputStream;


/**
 * @author P. WILLEMET
 */
public interface IoPoint extends Closeable {


    KGateInputStream getInputStream() throws Exception;

    OutputStream getOutputStream() throws Exception;


}
