package com.github.kwoin.kgate.http.message;

/**
 * @author P. WILLEMET
 */
public class HttpResponse extends HttpMessage {


    private String statusCode;
    private String reasonPhrase;


    public HttpResponse(byte[] original, String httpVersion, HttpHeader[] headers, String body, String statusCode, String reasonPhrase) {

        super(original, httpVersion, headers, body);
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;

    }


    public String getStatusCode() {

        return statusCode;

    }


    public String getReasonPhrase() {

        return reasonPhrase;

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
