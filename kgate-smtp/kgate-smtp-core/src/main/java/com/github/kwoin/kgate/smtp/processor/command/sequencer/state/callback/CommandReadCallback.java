package com.github.kwoin.kgate.smtp.processor.command.sequencer.state.callback;

import com.github.kwoin.kgate.core.processor.command.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.processor.command.sequencer.state.AbstractState;
import com.github.kwoin.kgate.core.processor.command.sequencer.state.callback.IStateCallback;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.SmtpStateMachineSequencer;


/**
 * @author P. WILLEMET
 */
public class CommandReadCallback implements IStateCallback {


    @Override
    public int run(byte[] dataRead, IStateMachine stateMachine, AbstractState callingState) {

        callingState.reset();

        stateMachine.setCurrentStateIndex(SmtpStateMachineSequencer.READ_REPLY_STATE);

        return IStateMachine.CUT;

    }

}
