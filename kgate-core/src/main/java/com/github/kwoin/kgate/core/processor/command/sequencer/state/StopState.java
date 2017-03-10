package com.github.kwoin.kgate.core.processor.command.sequencer.state;

import com.github.kwoin.kgate.core.processor.command.sequencer.IStateMachine;


/**
 * @author P. WILLEMET
 */
public class StopState extends AbstractState {


    public StopState(IStateMachine stateMachine) {

        super(stateMachine);

    }


    @Override
    public int push(byte b) {

        return IStateMachine.STOP;

    }
}
