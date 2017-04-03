package com.github.kwoin.kgate.http.sequencing.callback;


import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.core.sequencing.state.callback.SwitchStateCallback;
import com.github.kwoin.kgate.http.sequencing.HttpRequestSequencer;


/**
 * @author P. WILLEMET
 */
public class RequestReadMethodCallback extends SwitchStateCallback<HttpRequestSequencer> {


    public RequestReadMethodCallback() {

        super(HttpRequestSequencer.READ_REQUEST_URI, true);

    }


    @Override
    public int run(byte[] dataRead, HttpRequestSequencer stateMachine, AbstractState callingState) {

        String method = new String(dataRead, 0, dataRead.length - 1);
        stateMachine.getHttpMessage().setMethod(method);

        return super.run(dataRead, stateMachine, callingState);

    }



}
