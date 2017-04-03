package com.github.kwoin.kgate.http.sequencing.state_old;


import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceState;


/**
 * @author P. WILLEMET
 */
public class NoBodyState extends ReadUntilSequenceState {


    public NoBodyState(IStateMachine stateMachine) {

        super(stateMachine,
                "\r\n\r\n".getBytes(),
                null,
                null,
                null,
                false);

    }
}
