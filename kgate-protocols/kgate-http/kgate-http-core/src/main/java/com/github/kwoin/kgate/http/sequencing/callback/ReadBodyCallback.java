package com.github.kwoin.kgate.http.sequencing.callback;

import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.core.sequencing.state.callback.SwitchStateCallback;
import com.github.kwoin.kgate.http.sequencing.HttpMessageSequencer;


/**
 * @author P. WILLEMET
 */
public class ReadBodyCallback extends SwitchStateCallback<HttpMessageSequencer> {


    public ReadBodyCallback() {

        super(IStateMachine.CUT, true);

    }


    @Override
    public int run(byte[] dataRead, HttpMessageSequencer stateMachine, AbstractState callingState) {

        stateMachine.getHttpMessage().setBody(new String(dataRead));

        return super.run(dataRead, stateMachine, callingState);

    }
}
