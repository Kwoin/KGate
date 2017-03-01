package com.github.kwoin.kgate.core.processor.chain.command.sequencer.component.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ISequencer;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.component.StateMachineSequencerComponent;


/**
 * @author P. WILLEMET
 */
public abstract class AbstractState implements ISequencer {


    protected StateMachineSequencerComponent stateMachine;


    public AbstractState(StateMachineSequencerComponent stateMachine) {

        this.stateMachine = stateMachine;

    }


}