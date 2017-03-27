package com.github.kwoin.kgate.http.processor.command.sequencer.state;

import com.github.kwoin.kgate.sequencing.sequencer.IStateMachine;
import com.github.kwoin.kgate.sequencing.sequencer.state.ReadUntilSequenceState;


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
