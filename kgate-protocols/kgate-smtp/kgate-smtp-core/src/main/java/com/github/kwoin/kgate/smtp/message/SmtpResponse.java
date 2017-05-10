package com.github.kwoin.kgate.smtp.message;


import com.github.kwoin.kgate.core.message.Message;


/**
 * @author P. WILLEMET
 */
public class SmtpResponse extends Message {


    private int code;
    private String reasonPhrase;

    public SmtpResponse(byte[] original, int code, String reasonPhrase) {

        super(original);
        this.code = code;
        this.reasonPhrase = reasonPhrase;

    }


    public int getCode() {

        return code;

    }


    public String getReasonPhrase() {

        return reasonPhrase;

    }


}
