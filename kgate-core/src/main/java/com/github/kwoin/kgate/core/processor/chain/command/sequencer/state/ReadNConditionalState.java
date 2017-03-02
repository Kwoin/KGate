package com.github.kwoin.kgate.core.processor.chain.command.sequencer.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.StateMachineSequencer;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.callback.IStateCallback;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;


/**
 * @author P. WILLEMET
 */
public class ReadNConditionalState extends AbstractState {


    private ICondition condition;
    private int nBytes;
    private int cursor;
    private IStateCallback onSuccess;
    private IStateCallback onFail;
    private ByteArrayOutputStream baos;


    public ReadNConditionalState(
            StateMachineSequencer stateMachine,
            ICondition condition,
            int nBytes,
            @Nullable IStateCallback onSuccess,
            @Nullable IStateCallback onFail) {

        super(stateMachine);

        if(nBytes < 1)
            throw new IllegalArgumentException("nBytes must be >= 1");

        this.condition = condition;
        this.nBytes = nBytes;
        cursor = 0;
        this.onSuccess = onSuccess;
        this.onFail = onFail;
        baos = new ByteArrayOutputStream();

    }


    @Override
    public ESequencerResult push(byte b) {

        baos.write(b);

        if(condition.accept(b))
            cursor++;
        else
            return onFail != null ? onFail.run(baos.toByteArray(), stateMachine) : ESequencerResult.STOP;

        if(cursor == nBytes)
            return onSuccess != null ? onSuccess.run(baos.toByteArray(), stateMachine) : ESequencerResult.CUT;

        return ESequencerResult.CONTINUE;

    }


    @Override
    public void reset() {

        baos.reset();
        cursor = 0;

    }


    public interface ICondition {

        boolean accept(byte b);

    }

}
