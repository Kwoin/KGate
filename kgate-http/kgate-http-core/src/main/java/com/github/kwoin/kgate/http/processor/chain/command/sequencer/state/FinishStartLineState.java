package com.github.kwoin.kgate.http.processor.chain.command.sequencer.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.callback.SwitchStateCallback;
import com.github.kwoin.kgate.http.processor.chain.command.sequencer.HttpMessageStateMachineSequencer;


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
