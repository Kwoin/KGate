package com.github.kwoin.kgate.http.processor.command.sequencer.state.callback;

import com.github.kwoin.kgate.core.processor.command.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.processor.command.sequencer.state.AbstractState;
import com.github.kwoin.kgate.core.processor.command.sequencer.state.callback.IStateCallback;


/**
 * @author P. WILLEMET
 */
public class IsEndCallback implements IStateCallback {


    @Override
    public int run(byte[] dataRead, IStateMachine stateMachine, AbstractState callingState) {

        callingState.reset();
        return dataRead.length == 2 ? 3 : 0;

    }

}
