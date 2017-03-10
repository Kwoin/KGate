package com.github.kwoin.kgate.http.processor.chain.command.sequencer.state.callback;

import com.github.kwoin.kgate.core.processor.command.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.processor.command.sequencer.state.AbstractState;
import com.github.kwoin.kgate.core.processor.command.sequencer.state.callback.IStateCallback;
import com.github.kwoin.kgate.http.processor.chain.command.sequencer.HttpMessageStateMachineSequencer;


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
