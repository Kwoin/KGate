package com.github.kwoin.kgate.http.model;

import java.util.Map;


/**
 * @author P. WILLEMET
 */
public class HttpRequest extends HttpMessage {


    public static final String GET = "GET";
    public static final String HEAD = "HEAD";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    public static final String CONNECT = "CONNECT";
    public static final String OPTIONS = "OPTIONS";
    public static final String TRACE = "TRACE";

    private String method;
    private String requestUri;


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
