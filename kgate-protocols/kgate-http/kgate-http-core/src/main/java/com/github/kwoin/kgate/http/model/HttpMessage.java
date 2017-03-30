package com.github.kwoin.kgate.http.model;

import javax.annotation.Nullable;
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


    @Nullable
    public String getHeaderValue(String headerKey) {

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            if(entry.getKey().equalsIgnoreCase(headerKey))
                return entry.getValue();
        }

        return null;

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
