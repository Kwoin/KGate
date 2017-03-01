package com.github.kwoi.kgate.http.processor.chain.command.sequencer.component.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.component.StateMachineSequencerComponent;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.component.state.IStateCallback;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.component.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.component.state.SwitchStateCallback;


/**
 * @author P. WILLEMET
 */
public class ReadStartLineState extends ReadUntilSequenceState {


    public ReadStartLineState(StateMachineSequencerComponent stateMachine) {

        super(stateMachine,
                "\r\n".getBytes(),
                null,
                new SwitchStateCallback(new ReadHeadersState(stateMachine)),
                null);

    }



}
