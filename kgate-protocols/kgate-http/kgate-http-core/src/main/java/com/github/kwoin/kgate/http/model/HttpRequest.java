package com.github.kwoin.kgate.http.model;

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

}
