package com.github.kwoin.kgate.smtp.model;

/**
 * @author P. WILLEMET
 */
public class SmtpResponse extends SmtpMessage {


    private String statusCode;
    private String reasonPhrase = "";


    public String getStatusCode() {

        return statusCode;

    }


    public void setStatusCode(String statusCode) {

        this.statusCode = statusCode;

    }


    public String getReasonPhrase() {

        return reasonPhrase;

    }


    public void setReasonPhrase(String reasonPhrase) {

        this.reasonPhrase = reasonPhrase;

    }
}
