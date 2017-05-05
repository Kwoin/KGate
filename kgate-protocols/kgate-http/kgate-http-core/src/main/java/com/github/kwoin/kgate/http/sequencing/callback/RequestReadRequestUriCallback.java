package com.github.kwoin.kgate.http.sequencing.callback;

import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.core.sequencing.state.callback.SwitchStateCallback;
import com.github.kwoin.kgate.http.sequencing.HttpRequestSequencer;


/**
 * @author P. WILLEMET
 */
public class RequestReadRequestUriCallback extends SwitchStateCallback<HttpRequestSequencer> {


    public RequestReadRequestUriCallback() {

        super(HttpRequestSequencer.READ_HTTP_VERSION, true);

    }


    @Override
    public int run(byte[] dataRead, HttpRequestSequencer stateMachine, AbstractState callingState) {

        String requestUri = new String(dataRead, 0, dataRead.length - 1);
        stateMachine.getHttpMessage().setRequestUri(requestUri);

        return super.run(dataRead, stateMachine, callingState);

    }
}
