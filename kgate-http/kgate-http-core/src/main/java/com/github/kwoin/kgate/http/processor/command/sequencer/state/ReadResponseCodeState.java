package com.github.kwoin.kgate.http.processor.command.sequencer.state;

import com.github.kwoin.kgate.core.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.sequencer.state.ReadNBytesConditionalState;
import com.github.kwoin.kgate.core.sequencer.state.condition.DigitCondition;
import com.github.kwoin.kgate.http.processor.command.sequencer.state.callback.InterpretResponseCodeCallback;


/**
 * @author P. WILLEMET
 */
public class ReadResponseCodeState extends ReadNBytesConditionalState {


    public ReadResponseCodeState(IStateMachine stateMachine) {

        super(stateMachine,
                new DigitCondition(),
                3,
                new InterpretResponseCodeCallback(),
                null,
                true);

    }
}
