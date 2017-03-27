package com.github.kwoin.kgate.http.processor.command.sequencer.state;

import com.github.kwoin.kgate.sequencing.sequencer.IStateMachine;
import com.github.kwoin.kgate.sequencing.sequencer.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.sequencing.sequencer.state.callback.SwitchStateCallback;
import com.github.kwoin.kgate.http.processor.command.sequencer.HttpMessageStateMachineSequencer;


/**
 * @author P. WILLEMET
 */
public class FinishHeaderState extends ReadUntilSequenceState {


    public FinishHeaderState(IStateMachine stateMachine) {

        super(stateMachine,
                "\r\n".getBytes(),
                null,
                new SwitchStateCallback(HttpMessageStateMachineSequencer.READ_HEADER_STATE, true),
                null,
                false);

    }
}
