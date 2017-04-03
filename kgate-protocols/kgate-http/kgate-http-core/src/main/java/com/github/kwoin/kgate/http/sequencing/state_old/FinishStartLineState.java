package com.github.kwoin.kgate.http.sequencing.state_old;


import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.core.sequencing.state.callback.SwitchStateCallback;
import com.github.kwoin.kgate.http.sequencing.HttpMessageStateMachineSequencer;


/**
 * @author P. WILLEMET
 */
public class FinishStartLineState extends ReadUntilSequenceState {


    public FinishStartLineState(IStateMachine stateMachine) {

        super(stateMachine,
                "\r\n".getBytes(),
                null,
                new SwitchStateCallback(HttpMessageStateMachineSequencer.READ_HEADER_STATE, false),
                null,
                false);

    }
}
