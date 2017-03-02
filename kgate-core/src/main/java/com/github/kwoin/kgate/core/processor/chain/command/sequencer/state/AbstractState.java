package com.github.kwoin.kgate.core.processor.chain.command.sequencer.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ISequencer;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.StateMachineSequencer;


/**
 * @author P. WILLEMET
 */
public abstract class AbstractState implements ISequencer {


    protected StateMachineSequencer stateMachine;


    public AbstractState(StateMachineSequencer stateMachine) {

        this.stateMachine = stateMachine;

    }


    public abstract void reset();


}