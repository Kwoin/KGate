package com.github.kwoin.kgate.core.sequencing.state;

import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.callback.IStateCallback;

import javax.annotation.Nullable;


/**
 * @author P. WILLEMET
 */
public class ReadSequenceState<T extends IStateMachine> extends AbstractState {


    private byte[] sequence;
    private int sequenceCursor;
    private IStateCallback onSuccess;
    private IStateCallback onFail;


    public ReadSequenceState(
            T stateMachine,
            byte[] sequence,
            @Nullable IStateCallback onSuccess,
            @Nullable IStateCallback onFail) {

        super(stateMachine);
        this.sequence = sequence;
        this.sequenceCursor = 0;
        this.onSuccess = onSuccess;
        this.onFail = onFail;

    }


    @Override
    public int push(byte b) {

        if(sequence[sequenceCursor] == b)
            sequenceCursor++;
        else if(onFail != null) {
            byte[] dataRead = new byte[sequenceCursor + 1];
            System.arraycopy(sequence, 0, dataRead, 0, sequenceCursor);
            dataRead[dataRead.length - 1] = b;
            onFail.run(dataRead, stateMachine, this);
        } else
            return stateMachine.STOP;

        if(sequenceCursor == sequence.length)
            return onSuccess != null ? onSuccess.run(sequence, stateMachine, this) : stateMachine.CUT;

        return stateMachine.getCurrentStateIndex();

    }


    @Override
    public void reset() {

        sequenceCursor = 0;

    }

}
