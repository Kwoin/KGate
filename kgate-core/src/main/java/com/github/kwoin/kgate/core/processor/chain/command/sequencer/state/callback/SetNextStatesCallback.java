package com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.callback;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.StateMachineSequencer;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.AbstractState;

import java.util.ArrayList;


/**
 * @author P. WILLEMET
 */
public class SetNextStatesCallback implements IStateCallback {


    protected ArrayList<AbstractState> states;


    public SetNextStatesCallback() {

        this.states = new ArrayList<>();

    }


    public SetNextStatesCallback addState(AbstractState state) {

        states.add(state);
        return this;

    }


    @Override
    public ESequencerResult run(byte[] dataRead, StateMachineSequencer stateMachine) {

        for (AbstractState state : states)
            stateMachine.add(state);

        stateMachine.next();

        return ESequencerResult.CONTINUE;

    }

}
