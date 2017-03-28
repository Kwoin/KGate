package com.github.kwoin.kgate.http.sequencing;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.http.model.HttpRequest;


/**
 * @author P. WILLEMET
 */
public class HttpRequestSequencer extends HttpMessageSequencer<HttpRequest> {


    public static final int READ_METHOD = 0;
    public static final int READ_REQUEST_URI = 1;
    public static final int READ_HTTP_VERSION = 2;
    public static final int READ_HEADER = 3;
    public static final int READ_BODY = 4;
    public static final int READ_BODY_CHUNKED = 5;


    @Override
    public AbstractState[] initializeStates() {

        return new AbstractState[0];

    }


    @Override
    public int getReadHeaderStateIndex() {

        return READ_HEADER;

    }


    @Override
    public int getReadBodyStateIndex() {

        return READ_BODY;

    }


    @Override
    public int getReadBodyChunkedStateIndex() {

        return READ_BODY_CHUNKED;
    }


    @Override
    public void init(IContext context) {

        super.init(context);
        httpMessage = new HttpRequest();

    }



}
