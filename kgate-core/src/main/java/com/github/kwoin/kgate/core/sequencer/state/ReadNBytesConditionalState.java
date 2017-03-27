package com.github.kwoin.kgate.core.sequencer.state;

import com.github.kwoin.kgate.core.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.sequencer.state.callback.IStateCallback;
import com.github.kwoin.kgate.core.sequencer.state.condition.ICondition;

import javax.annotation.Nullable;


/**
 * @author P. WILLEMET
 */
public class ReadNBytesConditionalState extends AbstractState {


    private ICondition condition;
    private int nBytes;
    private int cursor;
    private IStateCallback onSuccess;
    private IStateCallback onFail;
    private boolean bufferize;


    public ReadNBytesConditionalState(
            IStateMachine stateMachine,
            ICondition condition,
            int nBytes,
            @Nullable IStateCallback onSuccess,
            @Nullable IStateCallback onFail,
            boolean bufferize) {

        super(stateMachine);

        if(nBytes < 1)
            throw new IllegalArgumentException("nBytes must be >= 1");

        this.condition = condition;
        this.nBytes = nBytes;
        cursor = 0;
        this.onSuccess = onSuccess;
        this.onFail = onFail;
        this.bufferize = bufferize;


    }


    @Override
    public int push(byte b) {

        if (bufferize)
            bufferize(b);

        if(condition.accept(b))
            cursor++;
        else
            return onFail != null ? onFail.run(getBuffer(), stateMachine, this) : stateMachine.STOP;

        if(cursor == nBytes)
            return onSuccess != null ? onSuccess.run(getBuffer(), stateMachine, this) : stateMachine.CUT;

        return stateMachine.getCurrentStateIndex();

    }


    @Override
    public void reset() {

        super.reset();

        cursor = 0;

    }

}
