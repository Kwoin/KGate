package com.github.kwoin.kgate.http.sequencing;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.sequencing.ESequencerResult;
import com.github.kwoin.kgate.core.sequencing.StateMachineSequencer;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.http.model.HttpMessage;


/**
 * @author P. WILLEMET
 */
public abstract class HttpMessageSequencer<T extends HttpMessage> extends StateMachineSequencer {


    public static final String HTTP_LAST_MESSAGE_FIELD = "http.lastMessage";
    public static final String HTTP_SEQUENCER_ERROR_FIELD = "http.sequencer.error";

    protected T httpMessage;


    public T getHttpMessage() {

        return httpMessage;

    }


    @Override
    public ESequencerResult push(byte b) {

        ESequencerResult result = super.push(b);

        if(result != ESequencerResult.CONTINUE)
            context.setVariable(IContext.ECoreScope.SESSION, HTTP_LAST_MESSAGE_FIELD, httpMessage);

        return result;

    }


    @Override
    public abstract AbstractState[] initializeStates();


    public abstract int getReadHeaderStateIndex();


    public abstract int getReadBodyStateIndex();


    public abstract int getReadBodyChunkedStateIndex();


}
