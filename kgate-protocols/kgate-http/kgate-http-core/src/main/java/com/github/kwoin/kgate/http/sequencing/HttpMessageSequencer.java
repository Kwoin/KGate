package com.github.kwoin.kgate.http.sequencing;

import com.github.kwoin.kgate.core.sequencing.StateMachineSequencer;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.http.model.HttpMessage;


/**
 * @author P. WILLEMET
 */
public abstract class HttpMessageSequencer<T extends HttpMessage> extends StateMachineSequencer {


    protected T httpMessage;


    public T getHttpMessage() {

        return httpMessage;

    }


    @Override
    public abstract AbstractState[] initializeStates();


    public abstract int getReadHeaderStateIndex();


    public abstract int getReadBodyStateIndex();


    public abstract int getReadBodyChunkedStateIndex();


}
