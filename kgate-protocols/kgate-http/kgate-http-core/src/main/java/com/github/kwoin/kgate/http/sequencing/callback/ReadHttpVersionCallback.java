package com.github.kwoin.kgate.http.sequencing.callback;

import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.core.sequencing.state.callback.SwitchStateCallback;
import com.github.kwoin.kgate.http.sequencing.HttpMessageSequencer;


/**
 * @author P. WILLEMET
 */
public class ReadHttpVersionCallback extends SwitchStateCallback<HttpMessageSequencer> {


    private int trailingSize;


    public ReadHttpVersionCallback(int targetState, int trailingSize) {

        super(targetState, true);
        this.trailingSize = trailingSize;

    }


    @Override
    public int run(byte[] dataRead, HttpMessageSequencer stateMachine, AbstractState callingState) {

        String httpVersion = new String(dataRead, 0, dataRead.length - trailingSize);
        stateMachine.getHttpMessage().setHttpVersion(httpVersion);

        return super.run(dataRead, stateMachine, callingState);

    }
}
