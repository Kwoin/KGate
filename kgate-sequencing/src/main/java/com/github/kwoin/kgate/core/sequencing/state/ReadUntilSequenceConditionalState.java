package com.github.kwoin.kgate.core.sequencing.state;

import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.callback.IStateCallback;
import com.github.kwoin.kgate.core.sequencing.state.condition.ICondition;

import javax.annotation.Nullable;


/**
 * @author P. WILLEMET
 */
public class ReadUntilSequenceConditionalState<T extends IStateMachine> extends AbstractState {


    private byte[] sequence;
    private int sequenceCursor;
    private ICondition condition;
    private IStateCallback onSuccess;
    private IStateCallback onFail;
    private boolean bufferize;


    public ReadUntilSequenceConditionalState(T stateMachine,
                                             ICondition condition,
                                             byte[] sequence,
                                             @Nullable IStateCallback onSuccess,
                                             @Nullable IStateCallback onFail,
                                             boolean bufferize) {

        super(stateMachine);
        this.condition = condition;
        this.sequence = sequence;
        this.sequenceCursor = 0;
        this.onSuccess = onSuccess;
        this.onFail = onFail;
        this.bufferize = bufferize;

    }


    @Override
    public int push(byte b) {

        if (bufferize)
            bufferize(b);

        if(sequence[sequenceCursor] == b) {
            sequenceCursor++;
            if(sequenceCursor == sequence.length)
                return onSuccess != null ? onSuccess.run(getBuffer(), stateMachine, this) : IStateMachine.CUT;
        } else {
            sequenceCursor = 0;
            if (!condition.accept(b))
                return onFail != null ? onFail.run(getBuffer(), stateMachine, this) : IStateMachine.STOP;
        }

        return stateMachine.getCurrentStateIndex();

    }


    @Override
    public void reset() {

        super.reset();
        sequenceCursor = 0;

    }
}
