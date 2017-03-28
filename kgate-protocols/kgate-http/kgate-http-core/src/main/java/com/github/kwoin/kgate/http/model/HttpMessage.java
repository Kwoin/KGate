package com.github.kwoin.kgate.http.model;

import java.util.HashMap;
import java.util.Map;


/**
 * @author P. WILLEMET
 */
public abstract class HttpMessage {


    protected HashMap<String, String> headers;
    protected String body;
    protected String httpVersion;


    public HttpMessage() {

        headers = new HashMap<>();
        body = "";

    }


    public Map<String, String> getHeaders() {

        return headers;

    }


    public String getBody() {

        return body;

    }


    public void setBody(String body) {

        this.body = body;

    }


    public String getHttpVersion() {

        return httpVersion;

    }


    public void setHttpVersion(String httpVersion) {

        this.httpVersion = httpVersion;

    }
}
