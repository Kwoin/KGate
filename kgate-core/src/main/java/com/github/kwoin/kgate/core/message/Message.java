package com.github.kwoin.kgate.core.message;

/**
 * @author P. WILLEMET
 */
public class Message {


    protected byte[] toBeTransmitted;


    public Message(byte[] original) {

        this.toBeTransmitted = original;

    }


    public byte[] getToBeTransmitted() {

        return toBeTransmitted;

    }


    public void setToBeTransmitted(byte[] toBeTransmitted) {

        this.toBeTransmitted = toBeTransmitted;

    }


}
