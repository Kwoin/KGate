package com.github.kwoin.kgate.http.sequencing.callback;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.core.sequencing.state.callback.IStateCallback;
import com.github.kwoin.kgate.http.model.HttpMessage;
import com.github.kwoin.kgate.http.model.HttpRequest;
import com.github.kwoin.kgate.http.model.HttpResponse;
import com.github.kwoin.kgate.http.sequencing.HttpMessageSequencer;
import com.github.kwoin.kgate.http.sequencing.HttpResponseSequencer;
import com.github.kwoin.kgate.http.sequencing.state.ReadBodyState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;


/**
 * @author P. WILLEMET
 */
public class ReadHeaderCallback implements IStateCallback<HttpMessageSequencer> {


    private final Logger logger = LoggerFactory.getLogger(ReadHeaderCallback.class);


    @Override
    public int run(byte[] dataRead, HttpMessageSequencer stateMachine, AbstractState callingState) {

        callingState.reset();

        // If empty line, compute the next state (which will be one of READ_BODY, READ_BODY_CHUNKED or CUT)
        if(dataRead.length == 2)
            return switchToReadBodyState(stateMachine);

        String key, value;
        int i = 0, read;
        ByteArrayOutputStream baos =  new ByteArrayOutputStream();

        // Read header key
        while((read = dataRead[i++]) != ':')
            baos.write(read);
        key = baos.toString().trim();

        // Read header value
        baos.reset();
        int limit = dataRead.length - 2;
        while(++i < limit)
            baos.write(dataRead[i]);
        value = baos.toString().trim();

        stateMachine.getHttpMessage().getHeaders().put(key, value);

        return stateMachine.getReadHeaderStateIndex();

    }


    /**
     * Based on RFC7230 3.3.3
     * @param stateMachine
     * @return
     */
    private int switchToReadBodyState(HttpMessageSequencer stateMachine) {

        // Responses to HEAD request have no body
        HttpMessage lastMessage = (HttpMessage) stateMachine.getContext().getVariable(IContext.ECoreScope.SESSION, HttpMessageSequencer.HTTP_LAST_MESSAGE_FIELD);
        if(lastMessage instanceof HttpRequest)
            if(((HttpRequest) lastMessage).getMethod().equals(HttpRequest.HEAD))
                return IStateMachine.CUT;

        // Responses with code 1xx, 204 or 304 have no body
        // Responses with code 2xx to a CONNECT request have no body
        HttpMessage currentMessage = stateMachine.getHttpMessage();
        if(currentMessage instanceof HttpResponse) {
            String statusCode = ((HttpResponse) currentMessage).getStatusCode();
            if(statusCode.charAt(0) == '1' || statusCode.equals("204") || statusCode.equals("304"))
                return IStateMachine.CUT;
            if(((HttpRequest) lastMessage).getMethod().equals(HttpRequest.CONNECT) && statusCode.charAt(0) == '2')
                return IStateMachine.CUT;
        }

        // Transfer-Encoding header finishing with "chunked" implies body will be chunked
        String transferEncoding = stateMachine.getHttpMessage().getHeaderValue("transfer-encoding");
        if(transferEncoding != null) {
            String[] values = transferEncoding.split(", ?");
            for (int i = 0; i < values.length; i++) {
                if(values[i].equals("chunked"))
                    if(i == values.length - 1)
                        return stateMachine.getReadBodyChunkedStateIndex();
                    else if(currentMessage instanceof HttpResponse)
                        return HttpResponseSequencer.READ_ENDLESS;
                    else {
                        HttpResponse errorResponse = new HttpResponse();
                        errorResponse.setHttpVersion("HTTP/1.1");
                        errorResponse.setStatusCode("400");
                        errorResponse.setReasonPhrase("Bad Request");
                        stateMachine.getContext().setVariable(IContext.ECoreScope.SESSION, HttpMessageSequencer.HTTP_SEQUENCER_ERROR_FIELD, errorResponse);
                        return IStateMachine.STOP;
                    }

            }
        }

        // Search Content-Length value
        String contentLengthStr = stateMachine.getHttpMessage().getHeaderValue("content-length");
        int contentLength = 0;
        if(contentLengthStr != null)
            try {
                contentLength = Integer.parseInt(contentLengthStr);
            } catch (NumberFormatException e) {
                logger.warn("Invalid content-length: " + contentLengthStr);
            }
        else
            if(currentMessage instanceof HttpResponse)
                return HttpResponseSequencer.READ_ENDLESS;

        if(contentLength == 0)
            return IStateMachine.CUT;
        else {
            ((ReadBodyState) stateMachine.getStates()[stateMachine.getReadBodyStateIndex()]).setNBytes(contentLength);
            return stateMachine.getReadBodyStateIndex();
        }

    }

}
