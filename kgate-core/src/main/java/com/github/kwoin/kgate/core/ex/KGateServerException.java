package com.github.kwoin.kgate.core.ex;

/**
 * @author P. WILLEMET
 */
public class KGateServerException extends Exception {


    public KGateServerException(Throwable cause) {

        super(cause);

    }


    public KGateServerException(String message, Throwable cause) {

        super(message, cause);

    }


    public KGateServerException(String message) {

        super(message);

    }

}