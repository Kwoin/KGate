package com.github.kwoin.kgate.http.sequencing.state_old;


import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.ReadNBytesConditionalState;
import com.github.kwoin.kgate.core.sequencing.state.condition.DigitCondition;
import com.github.kwoin.kgate.http.sequencing.state_old.callback.InterpretResponseCodeCallback;


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
