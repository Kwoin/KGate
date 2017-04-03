package com.github.kwoin.kgate.core.sequencing.state;

import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.callback.IStateCallback;
import com.github.kwoin.kgate.core.sequencing.state.condition.ICondition;

import javax.annotation.Nullable;


/**
 * @author P. WILLEMET
 */
public class ReadNBytesConditionalState<T extends IStateMachine> extends AbstractState {


    private ICondition condition;
    private int nBytes;
    private int cursor;
    private IStateCallback onSuccess;
    private IStateCallback onFail;
    private boolean bufferize;


    public ReadNBytesConditionalState(
            T stateMachine,
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
