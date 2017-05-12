package com.github.kwoin.kgate.http.sequencer;

import com.github.kwoin.kgate.http.message.HttpHeader;
import com.github.kwoin.kgate.http.message.HttpRequest;


/**
 * @author P. WILLEMET
 */
public class HttpRequestSequencer extends AbstractHttpSequencer<HttpRequest> {


    @Override
    protected boolean hasContent(String firstLine) {

        return true;

    }


    @Override
    protected HttpRequest newHttpMessage(byte[] toBeTransmitted, String firstLine, HttpHeader[] headers, String content) {

        String[] splittedFirstLine = firstLine.split(" ");
        String method = splittedFirstLine[0];
        String requestUri = splittedFirstLine[1];
        String httpVersion = splittedFirstLine[2];

        ((HttpResponseSequencer) session.getOppositeSession().getSequencer()).setPreviousRequestMethod(method);

        return new HttpRequest(toBeTransmitted, httpVersion, headers, content, method, requestUri);

    }

}
