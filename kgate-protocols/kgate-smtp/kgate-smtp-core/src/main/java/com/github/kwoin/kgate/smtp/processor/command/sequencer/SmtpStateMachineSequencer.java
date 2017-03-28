package com.github.kwoin.kgate.smtp.processor.command.sequencer;

import com.github.kwoin.kgate.sequencing.sequencer.StateMachineSequencer;
import com.github.kwoin.kgate.sequencing.sequencer.state.AbstractState;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.state.ReadCommandState;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.state.ReadDataState;
import com.github.kwoin.kgate.smtp.processor.command.sequencer.state.ReadReplyState;


/**
 * @author P. WILLEMET
 */
public class SmtpStateMachineSequencer extends StateMachineSequencer {


    public static final int READ_COMMAND_STATE = 0;
    public static final int READ_REPLY_STATE = 1;
    public static final int READ_DATA_STATE = 2;


    public SmtpStateMachineSequencer() {

        super(false);

    }


    @Override
    public AbstractState[] initializeStates() {

        return new AbstractState[] {
                new ReadCommandState(this),
                new ReadReplyState(this),
                new ReadDataState(this)
        };

    }
}
