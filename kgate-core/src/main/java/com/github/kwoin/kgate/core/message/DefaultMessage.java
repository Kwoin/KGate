package com.github.kwoin.kgate.core.message;

/**
 * @author P. WILLEMET
 */
public class DefaultMessage extends Message {


    public DefaultMessage(byte[] original) {

        super(original);

    }


    @Override
    protected byte[] toByteArray() {

        return toBeTransmitted;

    }

}
