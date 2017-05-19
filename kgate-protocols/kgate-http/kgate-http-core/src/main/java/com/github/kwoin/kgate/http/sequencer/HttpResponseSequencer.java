package com.github.kwoin.kgate.http.sequencer;

import com.github.kwoin.kgate.http.message.HttpHeader;
import com.github.kwoin.kgate.http.message.HttpResponse;

import javax.annotation.Nullable;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public class HttpResponseSequencer extends AbstractHttpSequencer<HttpResponse> {


    @Nullable
    private String previousRequestMethod;


    public void setPreviousRequestMethod(String previousRequestMethod) {

        this.previousRequestMethod = previousRequestMethod;

    }


    @Override
    protected boolean hasContent(String firstLine) {

        String statusCode = firstLine.split(" ")[1];

        return !("HEAD".equalsIgnoreCase(previousRequestMethod)
                || statusCode.charAt(0) == '1'
                || statusCode.equals("204")
                || statusCode.equals("304")
                || "CONNECT".equalsIgnoreCase(previousRequestMethod) && statusCode.charAt(0) == '2');

    }


    @Override
    protected HttpResponse newHttpMessage(byte[] original, String firstLine, List<HttpHeader> headers, String content) {

        String[] splittedFirstLine = firstLine.split(" ");
        String httpVersion = splittedFirstLine[0];
        String statusCode = splittedFirstLine[1];
        String reasonphrase = splittedFirstLine[2];

        previousRequestMethod = null;

        return new HttpResponse(original, httpVersion, headers, content, statusCode, reasonphrase);

    }


}
