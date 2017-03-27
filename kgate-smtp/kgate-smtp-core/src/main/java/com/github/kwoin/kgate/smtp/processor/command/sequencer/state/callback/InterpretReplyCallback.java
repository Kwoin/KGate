package com.github.kwoin.kgate.smtp.processor.command.sequencer.state.callback;

import com.github.kwoin.kgate.core.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.sequencer.state.AbstractState;
import com.github.kwoin.kgate.core.sequencer.state.callback.IStateCallback;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.SmtpStateMachineSequencer;


/**
 * @author P. WILLEMET
 */
public class InterpretReplyCallback implements IStateCallback {


    private static final byte[] START_MAIL_REPLY_CODE = "354".getBytes();


    @Override
    public int run(byte[] dataRead, IStateMachine stateMachine, AbstractState callingState) {

        callingState.reset();

        if(dataRead[4] == '-')
            stateMachine.setCurrentStateIndex(SmtpStateMachineSequencer.READ_REPLY_STATE);

        int cursor = 0;
        while(dataRead[cursor] == START_MAIL_REPLY_CODE[cursor] && cursor < START_MAIL_REPLY_CODE.length)
            cursor++;

        stateMachine.setCurrentStateIndex(cursor == START_MAIL_REPLY_CODE.length
                ? SmtpStateMachineSequencer.READ_DATA_STATE
                : SmtpStateMachineSequencer.READ_COMMAND_STATE);

        return IStateMachine.CUT;

    }

}
