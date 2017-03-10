package com.github.kwoin.kgate.http.processor.command.sequencer.state;

import com.github.kwoin.kgate.core.processor.command.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.processor.command.sequencer.state.ReadNBytesConditionalState;
import com.github.kwoin.kgate.core.processor.command.sequencer.state.condition.DigitCondition;
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
