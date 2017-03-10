package com.github.kwoin.kgate.http.processor.command.sequencer.state.callback;

import com.github.kwoin.kgate.core.processor.command.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.processor.command.sequencer.state.AbstractState;
import com.github.kwoin.kgate.core.processor.command.sequencer.state.callback.IStateCallback;
import com.github.kwoin.kgate.http.processor.command.sequencer.HttpMessageStateMachineSequencer;


/**
 * @author P. WILLEMET
 */
public class InterpretResponseCodeCallback implements IStateCallback {


    private static final byte ZERO = (byte) '0';
    private static final byte ONE = (byte) '1';
    private static final byte TWO = (byte) '2';
    private static final byte THREE = (byte) '3';
    private static final byte FOUR = (byte) '4';


    @Override
    public int run(byte[] dataRead, IStateMachine stateMachine, AbstractState callingState) {

        if(dataRead[0] == ONE)
            return HttpMessageStateMachineSequencer.NO_BODY_STATE;

        if((dataRead[0] == TWO || dataRead[0] == THREE) && dataRead[1] == ZERO && dataRead[2] == FOUR)
            return HttpMessageStateMachineSequencer.NO_BODY_STATE;

        return HttpMessageStateMachineSequencer.FINISH_START_LINE_STATE;

    }

}
