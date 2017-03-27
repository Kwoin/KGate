package com.github.kwoin.kgate.smtp.processor.command.sequencer.state;

import com.github.kwoin.kgate.sequencing.sequencer.IStateMachine;
import com.github.kwoin.kgate.sequencing.sequencer.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.sequencing.sequencer.state.callback.SwitchStateCallback;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.SmtpStateMachineSequencer;


/**
 * @author P. WILLEMET
 */
public class ReadDataState extends ReadUntilSequenceState {


    public ReadDataState(IStateMachine stateMachine) {

        super(stateMachine,
                "\r\n.\r\n".getBytes(),
                null,
                new SwitchStateCallback(SmtpStateMachineSequencer.READ_COMMAND_STATE, true),
                null,
                false);

    }
}
