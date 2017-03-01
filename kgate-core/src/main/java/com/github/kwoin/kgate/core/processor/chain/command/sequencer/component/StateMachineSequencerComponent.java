package com.github.kwoin.kgate.core.processor.chain.command.sequencer.component;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ISequencer;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.component.state.AbstractState;


/**
 * @author P. WILLEMET
 */
public class StateMachineSequencerComponent implements ISequencer {


    protected AbstractState currentState;


    public StateMachineSequencerComponent(AbstractState initialState) {

        currentState = initialState;

    }


    public void setCurrentState(AbstractState currentState) {

        this.currentState = currentState;

    }


    @Override
    public ESequencerResult push(byte b) {

        return currentState.push(b);

    }


}
