package com.github.kwoin.kgate.http.processor.command.sequencer.state.callback;

import com.github.kwoin.kgate.core.processor.command.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.processor.command.sequencer.state.AbstractState;
import com.github.kwoin.kgate.core.processor.command.sequencer.state.callback.IStateCallback;
import com.github.kwoin.kgate.http.processor.command.sequencer.HttpMessageStateMachineSequencer;


/**
 * @author P. WILLEMET
 */
public class InterpretTransferEncodingCallback implements IStateCallback {


    private final byte[] CHUNKED = " chunked ".getBytes();


    @Override
    public int run(byte[] dataRead, IStateMachine stateMachine, AbstractState callingState) {

        int cursor = 0;
        for (int i = 0; i < dataRead.length - 2; i++) {
            byte b = dataRead[i];
            if(b == CHUNKED[cursor]) {
                cursor++;
                if(cursor == CHUNKED.length) {
                    ((HttpMessageStateMachineSequencer) stateMachine).setChunked(true);
                }
            } else
                cursor = 0;
        }

        if(cursor == CHUNKED.length - 1)
            ((HttpMessageStateMachineSequencer) stateMachine).setChunked(true);

        return HttpMessageStateMachineSequencer.READ_HEADER_STATE;

    }

}
