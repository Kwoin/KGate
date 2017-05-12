package com.github.kwoin.kgate.http.message;

import com.github.kwoin.kgate.core.message.Message;

import javax.annotation.Nullable;


/**
 * @author P. WILLEMET
 */
public abstract class HttpMessage extends Message {


    protected HttpHeader[] headers;
    @Nullable protected String body;
    protected String httpVersion;


    public HttpMessage(byte[] original, String httpVersion, HttpHeader[] headers, String body) {

        super(original);

        this.httpVersion = httpVersion;
        this.headers = headers;
        this.body = body;

    }


    public HttpHeader[] getHeaders() {

        return headers;

    }


    @Nullable
    public String getHeaderValue(String headerKey) {

        for (HttpHeader header : headers) {
            if(header.getKey().equalsIgnoreCase(headerKey))
                return header.getValue();
        }

        return null;

    }


    public String getBody() {

        return body;

    }


    public String getHttpVersion() {

        return httpVersion;

    }


}
