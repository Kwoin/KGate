package com.github.kwoin.kgate.smtp.processor.command.sequencer.callback;


import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.core.sequencing.state.callback.IStateCallback;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.SmtpRequestSequencer;


/**
 * @author P. WILLEMET
 */
public class ReadCommandCallback implements IStateCallback<SmtpRequestSequencer> {


    @Override
    public int run(byte[] dataRead, SmtpRequestSequencer stateMachine, AbstractState callingState) {

        String command = new String(dataRead, 0, dataRead.length - 1);
        stateMachine.getSmtpMessage().setCommand(command);

        if(command.equals("DATA")) {
            stateMachine.setCurrentStateIndex(SmtpRequestSequencer.READ_DATA);
            return IStateMachine.CUT;
        } else
            return SmtpRequestSequencer.READ_COMMAND_VALUE;

    }
}
