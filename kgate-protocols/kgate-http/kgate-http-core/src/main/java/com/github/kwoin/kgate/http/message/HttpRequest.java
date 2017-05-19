package com.github.kwoin.kgate.http.message;

import java.util.List;


/**
 * @author P. WILLEMET
 */
public class HttpRequest extends HttpMessage {


    private String method;
    private String requestUri;


    public HttpRequest(byte[] toBeTransmitted, String httpVersion, List<HttpHeader> headers, String body, String method, String requestUri) {

        super(toBeTransmitted, httpVersion, headers, body);
        this.method = method;
        this.requestUri = requestUri;

    }


    public String getMethod() {

        return method;

    }


    public void setMethod(String method) {

        this.method = method;

    }


    public String getRequestUri() {

        return requestUri;

    }


    public void setRequestUri(String requestUri) {

        this.requestUri = requestUri;

    }


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder()
                .append(method)
                .append(" ")
                .append(requestUri)
                .append(" ")
                .append(httpVersion)
                .append("\r\n");
        for (HttpHeader header : headers)
            sb.append(header.getKey())
                    .append(": ")
                    .append(header.getValue())
                    .append("\r\n");
        sb.append("\r\n")
                .append(body);

        return sb.toString();

    }


}
