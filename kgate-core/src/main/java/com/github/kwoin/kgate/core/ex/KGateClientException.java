package com.github.kwoin.kgate.core.ex;

/**
 * @author P. WILLEMET
 */
public class KGateClientException extends Exception {


    public KGateClientException(Throwable cause) {

        super(cause);

    }


    public KGateClientException(String message, Throwable cause) {

        super(message, cause);

    }


    public KGateClientException(String message) {

        super(message);

    }

}