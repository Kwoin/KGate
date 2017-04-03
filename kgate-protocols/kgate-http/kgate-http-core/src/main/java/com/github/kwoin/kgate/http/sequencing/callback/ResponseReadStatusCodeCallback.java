package com.github.kwoin.kgate.http.sequencing.callback;

import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.core.sequencing.state.callback.SwitchStateCallback;
import com.github.kwoin.kgate.http.sequencing.HttpResponseSequencer;


/**
 * @author P. WILLEMET
 */
public class ResponseReadStatusCodeCallback extends SwitchStateCallback<HttpResponseSequencer> {


    public ResponseReadStatusCodeCallback() {

        super(HttpResponseSequencer.READ_REASON_PHRASE, false);

    }


    @Override
    public int run(byte[] dataRead, HttpResponseSequencer stateMachine, AbstractState callingState) {

        String statusCode = new String(dataRead, 0, dataRead.length - 1);
        stateMachine.getHttpMessage().setStatusCode(statusCode);

        return super.run(dataRead, stateMachine, callingState);

    }

}
