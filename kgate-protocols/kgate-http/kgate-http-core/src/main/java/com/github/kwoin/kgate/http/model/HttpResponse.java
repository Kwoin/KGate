package com.github.kwoin.kgate.http.model;

/**
 * @author P. WILLEMET
 */
public class HttpResponse extends HttpMessage {


    private String statusCode;
    private String reasonPhrase;


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
