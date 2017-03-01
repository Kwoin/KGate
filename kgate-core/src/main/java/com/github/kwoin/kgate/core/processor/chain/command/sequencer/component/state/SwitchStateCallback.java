package com.github.kwoin.kgate.core.processor.chain.command.sequencer.component.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.component.StateMachineSequencerComponent;


/**
 * @author P. WILLEMET
 */
public class SwitchStateCallback implements IStateCallback {


    protected AbstractState targetState;


    public SwitchStateCallback(AbstractState targetState) {

        this.targetState = targetState;

    }


    @Override
    public ESequencerResult run(byte[] dataRead, StateMachineSequencerComponent stateMachine) {

        stateMachine.setCurrentState(targetState);
        return ESequencerResult.CONTINUE;

    }

}
