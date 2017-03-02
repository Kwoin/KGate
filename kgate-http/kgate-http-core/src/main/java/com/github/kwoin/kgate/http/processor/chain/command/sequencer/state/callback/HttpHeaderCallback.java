package com.github.kwoin.kgate.http.processor.chain.command.sequencer.state.callback;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.StateMachineSequencer;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.AbstractState;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.callback.IStateCallback;
import com.github.kwoin.kgate.http.processor.chain.command.sequencer.AbstractHttpMessageStateMachineSequencer;


/**
 * @author P. WILLEMET
 */
public class HttpHeaderCallback implements IStateCallback {


    @Override
    public ESequencerResult run(byte[] dataRead, StateMachineSequencer stateMachine) {

        String line = new String(dataRead);
        if(line.equals("\r\n")) {
            AbstractState readBodyState = ((AbstractHttpMessageStateMachineSequencer)stateMachine).computeReadBodyState();
            stateMachine.add(readBodyState);
            stateMachine.next();
            return ESequencerResult.CONTINUE;
        }

        String header = line.substring(0, line.indexOf(':'));
        if (header.equals("Content-Length")) {
            String value = line.substring(line.indexOf(':') + 1, line.length() - 1).trim();
            ((AbstractHttpMessageStateMachineSequencer)stateMachine).setContentLength(Integer.parseInt(value));
        } else if(header.equals("Transfer-Encoding"))

    }
}
