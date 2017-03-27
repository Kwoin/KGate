package com.github.kwoin.kgate.http.processor.command.sequencer.state;

import com.github.kwoin.kgate.sequencing.sequencer.IStateMachine;
import com.github.kwoin.kgate.sequencing.sequencer.StateMachineSequencer;
import com.github.kwoin.kgate.sequencing.sequencer.state.AbstractState;
import com.github.kwoin.kgate.sequencing.sequencer.state.ReadSequenceState;
import com.github.kwoin.kgate.sequencing.sequencer.state.ReadUntilSequenceConditionalState;
import com.github.kwoin.kgate.sequencing.sequencer.state.callback.SwitchStateCallback;
import com.github.kwoin.kgate.sequencing.sequencer.state.condition.DigitCondition;
import com.github.kwoin.kgate.http.processor.command.sequencer.HttpMessageStateMachineSequencer;


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
