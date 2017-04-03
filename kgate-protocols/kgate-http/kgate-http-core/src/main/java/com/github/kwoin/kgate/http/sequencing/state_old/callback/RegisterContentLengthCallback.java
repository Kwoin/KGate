package com.github.kwoin.kgate.http.sequencing.state_old.callback;


import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.core.sequencing.state.callback.IStateCallback;
import com.github.kwoin.kgate.http.sequencing.HttpMessageStateMachineSequencer;


/**
 * @author P. WILLEMET
 */
public class RegisterContentLengthCallback implements IStateCallback {


    @Override
    public int run(byte[] dataRead, IStateMachine stateMachine, AbstractState callingState) {

        int contentLength = Integer.parseInt(new String(dataRead, 0, dataRead.length - 2));
        ((HttpMessageStateMachineSequencer) stateMachine).setContentLength(contentLength);

        return HttpMessageStateMachineSequencer.READ_HEADER_STATE;

    }

}
