package com.github.kwoin.kgate.http.processor.chain.command.sequencer.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.ReadUntilSequenceState;


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
