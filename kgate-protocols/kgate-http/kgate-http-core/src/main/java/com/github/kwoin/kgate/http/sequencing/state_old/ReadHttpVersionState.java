package com.github.kwoin.kgate.http.sequencing.state_old;


import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.StateMachineSequencer;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.core.sequencing.state.ReadSequenceState;
import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceConditionalState;
import com.github.kwoin.kgate.core.sequencing.state.callback.SwitchStateCallback;
import com.github.kwoin.kgate.core.sequencing.state.condition.DigitCondition;
import com.github.kwoin.kgate.http.sequencing.HttpMessageStateMachineSequencer;


/**
 * @author P. WILLEMET
 */
public class ReadHttpVersionState extends AbstractState implements IStateMachine {


    private final AbstractState[] states = {
            new ReadSequenceState(this, "HTTP/".getBytes(), new SwitchStateCallback(1, false), null),
            new ReadUntilSequenceConditionalState(this, new DigitCondition(), ".".getBytes(), new SwitchStateCallback(2, false), null, false),
            new ReadUntilSequenceConditionalState(this, new DigitCondition(), " ".getBytes(), null, null, false)
    };
    private int currentStateIndex;


    public ReadHttpVersionState(StateMachineSequencer stateMachine) {

        super(stateMachine);
        currentStateIndex = 0;

    }


    @Override
    public int getCurrentStateIndex() {

        return currentStateIndex;

    }


    @Override
    public void setCurrentStateIndex(int currentStateIndex) {

        this.setCurrentStateIndex(currentStateIndex);

    }


    @Override
    public AbstractState[] getStates() {

        return states;

    }


    @Override
    public int push(byte b) {

        int result = states[currentStateIndex].push(b);
        if(result == CUT)
            return HttpMessageStateMachineSequencer.READ_RESPONSE_CODE_STATE;
        else if(result == STOP)
            return HttpMessageStateMachineSequencer.FINISH_START_LINE_STATE;
        else
            currentStateIndex = result;

        return stateMachine.getCurrentStateIndex();

    }


    @Override
    public void reset() {

        currentStateIndex = 0;
        for (AbstractState state : states)
            state.reset();

    }
}
