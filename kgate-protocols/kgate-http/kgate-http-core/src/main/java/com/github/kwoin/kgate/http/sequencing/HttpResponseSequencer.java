package com.github.kwoin.kgate.http.sequencing;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.http.model.HttpResponse;
import com.github.kwoin.kgate.http.sequencing.state.ReadBodyChunkedState;
import com.github.kwoin.kgate.http.sequencing.state.ReadBodyState;
import com.github.kwoin.kgate.http.sequencing.state.ReadEndlessState;
import com.github.kwoin.kgate.http.sequencing.state.ReadHeaderState;
import com.github.kwoin.kgate.http.sequencing.state.ResponseReadHttpVersionState;
import com.github.kwoin.kgate.http.sequencing.state.ResponseReadReasonPhraseState;
import com.github.kwoin.kgate.http.sequencing.state.ResponseReadStatusCodeState;


/**
 * @author P. WILLEMET
 */
public class HttpResponseSequencer extends HttpMessageSequencer<HttpResponse> {


    public static final int READ_HTTP_VERSION = 0;
    public static final int READ_STATUS_CODE = 1;
    public static final int READ_REASON_PHRASE = 2;
    public static final int READ_HEADER = 3;
    public static final int READ_BODY = 4;
    public static final int READ_BODY_CHUNKED = 5;
    public static final int READ_ENDLESS = 6;


    @Override
    public AbstractState[] initializeStates() {

        return new AbstractState[] {
                new ResponseReadHttpVersionState(this),
                new ResponseReadStatusCodeState(this),
                new ResponseReadReasonPhraseState(this),
                new ReadHeaderState(this),
                new ReadBodyState(this),
                new ReadBodyChunkedState(this),
                new ReadEndlessState(this)
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
        httpMessage = new HttpResponse();

    }


    @Override
    public void reset() {

        super.reset();
        httpMessage = new HttpResponse();

    }

}
