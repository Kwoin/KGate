package com.github.kwoin.kgate.http.sequencing;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.http.model.HttpRequest;
import com.github.kwoin.kgate.http.sequencing.state.ReadBodyChunkedState;
import com.github.kwoin.kgate.http.sequencing.state.ReadBodyState;
import com.github.kwoin.kgate.http.sequencing.state.ReadHeaderState;
import com.github.kwoin.kgate.http.sequencing.state.RequestReadHttpVersionState;
import com.github.kwoin.kgate.http.sequencing.state.RequestReadMethodState;
import com.github.kwoin.kgate.http.sequencing.state.RequestReadRequestUriState;


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

        return new AbstractState[] {
                new RequestReadMethodState(this),
                new RequestReadRequestUriState(this),
                new RequestReadHttpVersionState(this),
                new ReadHeaderState(this),
                new ReadBodyState(this),
                new ReadBodyChunkedState(this)
        };

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
    public void init(IContext context, IoPoint inputPoint) {

        super.init(context, inputPoint);
        httpMessage = new HttpRequest();

    }


    @Override
    public void reset() {

        super.reset();
        httpMessage = new HttpRequest();

    }



}
