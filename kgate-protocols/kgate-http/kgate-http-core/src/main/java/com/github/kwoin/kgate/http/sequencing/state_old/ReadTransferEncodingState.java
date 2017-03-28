package com.github.kwoin.kgate.http.sequencing.state_old;


import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.http.sequencing.state_old.callback.InterpretTransferEncodingCallback;


/**
 * @author P. WILLEMET
 */
public class ReadTransferEncodingState extends ReadUntilSequenceState {


    public ReadTransferEncodingState(IStateMachine stateMachine) {

        super(stateMachine,
                "\r\n".getBytes(),
                null,
                new InterpretTransferEncodingCallback(),
                null,
                true);

    }
}
