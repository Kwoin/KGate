package com.github.kwoin.kgate.core.processor.chain.command.sequencer.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.IStateMachineSequencer;


/**
 * @author P. WILLEMET
 */
public abstract class AbstractState {


    protected IStateMachineSequencer stateMachine;


    public AbstractState(IStateMachineSequencer stateMachine) {

        this.stateMachine = stateMachine;

    }


    public abstract int push(byte b);

    public abstract void reset();


}