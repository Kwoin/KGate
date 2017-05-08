package com.github.kwoin.kgate.core.message;

/**
 * @author P. WILLEMET
 */
public class Message {


    protected byte[] original;


    public Message(byte[] original) {

        this.original = original;

    }


    public byte[] getOriginal() {

        return original;

    }


}
