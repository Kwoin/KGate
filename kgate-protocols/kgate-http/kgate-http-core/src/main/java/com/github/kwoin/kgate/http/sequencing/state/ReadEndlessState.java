package com.github.kwoin.kgate.http.sequencing.state;

import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.http.sequencing.HttpResponseSequencer;


/**
 * @author P. WILLEMET
 */
public class ReadEndlessState extends AbstractState<HttpResponseSequencer> {


    public ReadEndlessState(HttpResponseSequencer stateMachine) {

        super(stateMachine);

    }


    @Override
    public int push(byte b) {

        if(b != (byte)-1) {
            bufferize(b);
            return stateMachine.getCurrentStateIndex();
        } else {
            stateMachine.getHttpMessage().setBody(new String(getBuffer()));
            return IStateMachine.CUT;
        }

    }
}
