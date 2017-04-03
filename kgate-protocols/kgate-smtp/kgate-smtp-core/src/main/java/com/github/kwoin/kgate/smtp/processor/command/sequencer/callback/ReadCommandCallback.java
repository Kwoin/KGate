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

        String[] commandStr = new String(dataRead, 0, dataRead.length - 2).split(" ", 2);
        String command = commandStr[0];
        String params = commandStr.length == 2 ? commandStr[1] : "";
        stateMachine.getSmtpMessage().setCommand(command);
        stateMachine.getSmtpMessage().setParams(params);

        if(command.equals("DATA"))
            stateMachine.setCurrentStateIndex(SmtpRequestSequencer.READ_DATA);

        return IStateMachine.CUT;

    }
}
