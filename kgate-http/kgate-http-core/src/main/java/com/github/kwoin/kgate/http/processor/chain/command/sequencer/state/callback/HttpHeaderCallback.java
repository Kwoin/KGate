package com.github.kwoin.kgate.http.processor.chain.command.sequencer.state.callback;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.StateMachineSequencer;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.callback.IStateCallback;


/**
 * @author P. WILLEMET
 */
public class HttpHeaderCallback implements IStateCallback {


    @Override
    public ESequencerResult run(byte[] dataRead, StateMachineSequencer stateMachine) {

        String line = new String(dataRead);
        if(line.equals("\r\n")) {
            stateMachine.a
        }

        String header = line.substring(0, header.indexOf(':'));
        if header.equals()

    }
}
