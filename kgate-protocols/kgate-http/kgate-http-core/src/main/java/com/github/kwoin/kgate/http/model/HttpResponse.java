package com.github.kwoin.kgate.http.model;

import java.util.Map;


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


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder()
                .append(httpVersion)
                .append(" ")
                .append(statusCode)
                .append(" ")
                .append(reasonPhrase)
                .append("\r\n");
        for (Map.Entry<String, String> entry : headers.entrySet())
            sb.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("\r\n");
        sb.append("\r\n")
                .append(body);

        return sb.toString();

    }

}
