package com.github.kwoin.kgate.http.message;

import com.github.kwoin.kgate.core.message.Message;

import javax.annotation.Nullable;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public abstract class HttpMessage extends Message {


    protected final List<HttpHeader> headers;
    @Nullable protected String body;
    protected String httpVersion;


    public HttpMessage(byte[] original, String httpVersion, List<HttpHeader> headers, String body) {

        super(original);

        this.httpVersion = httpVersion;
        this.headers = headers;
        this.body = body;

    }


    public List<HttpHeader> getHeaders() {

        return headers;

    }


    @Nullable
    public HttpHeader getHeader(String headerKey) {

        for (HttpHeader header : headers) {
            if(header.getKey().equalsIgnoreCase(headerKey))
                return header;
        }

        return null;

    }


    public String getBody() {

        return body;

    }


    public void setBody(@Nullable String body) {

        this.body = body;

    }


    public String getHttpVersion() {

        return httpVersion;

    }


    public void setHttpVersion(String httpVersion) {

        this.httpVersion = httpVersion;

    }


    @Override
    public void commit() {

        fixMessageHeaders();
        super.commit();

    }


    @Override
    protected byte[] toByteArray() {

        return toString().getBytes();

    }


    protected void fixMessageHeaders() {

        HttpHeader transferEncodingHeader = getHeader("Transfer-Encoding");
        if(transferEncodingHeader != null && transferEncodingHeader.getValue().contains("chunked")) {
            String value = transferEncodingHeader.getValue();
            value = value.replace("chunked", "");
            if(value.trim().equals(""))
                headers.remove(transferEncodingHeader);
            else
                transferEncodingHeader.setValue(value.replace("  ", " "));
        }

        HttpHeader contentLengthHeader = getHeader("Content-Length");
        if(body == null) {
            if(contentLengthHeader != null)
                contentLengthHeader.setValue("0");
        } else {
            String contentLengthValue = String.valueOf(body.getBytes().length);
            if(contentLengthHeader == null)
                headers.add(new HttpHeader("content-length", contentLengthValue));
            else
                contentLengthHeader.setValue(contentLengthValue);
        }

    }

}
