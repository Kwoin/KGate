package com.github.kwoin.kgate.smtp.processor.command.sequencer.state;

import com.github.kwoin.kgate.core.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.sequencer.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.core.sequencer.state.callback.SwitchStateCallback;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.SmtpStateMachineSequencer;


/**
 * @author P. WILLEMET
 */
public class ReadCommandState extends ReadUntilSequenceState {


    public ReadCommandState(IStateMachine stateMachine) {

        super(stateMachine,
                "\r\n".getBytes(),
                null,
                new SwitchStateCallback(SmtpStateMachineSequencer.READ_REPLY_STATE, true),
                null,
                false);

    }
}
