package com.github.kwoin.kgate.http.sequencing.callback;

import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.core.sequencing.state.callback.SwitchStateCallback;
import com.github.kwoin.kgate.http.sequencing.HttpResponseSequencer;


/**
 * @author P. WILLEMET
 */
public class ResponseReadReasonPhraseCallback extends SwitchStateCallback<HttpResponseSequencer> {


    public ResponseReadReasonPhraseCallback() {

        super(HttpResponseSequencer.READ_HEADER, false);

    }


    @Override
    public int run(byte[] dataRead, HttpResponseSequencer stateMachine, AbstractState callingState) {

        String reasonPhrase = new String(dataRead, 0, dataRead.length - 2);
        stateMachine.getHttpMessage().setReasonPhrase(reasonPhrase);

        return super.run(dataRead, stateMachine, callingState);

    }
}
