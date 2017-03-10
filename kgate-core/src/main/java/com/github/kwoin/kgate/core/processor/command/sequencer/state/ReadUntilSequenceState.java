package com.github.kwoin.kgate.core.processor.command.sequencer.state;

import com.github.kwoin.kgate.core.processor.command.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.processor.command.sequencer.state.callback.IStateCallback;

import javax.annotation.Nullable;


/**
 * @author P. WILLEMET
 */
public class ReadUntilSequenceState extends AbstractState {


    private byte[] successSequence;
    private byte[] stopSequence;
    private int successSequenceCursor;
    private int stopSequenceCursor;
    private IStateCallback onSuccess;
    private IStateCallback onStop;
    private boolean bufferize;


    public ReadUntilSequenceState(
            IStateMachine stateMachine,
            byte[] successSequence,
            @Nullable byte[] stopSequence,
            @Nullable IStateCallback onSuccess,
            @Nullable IStateCallback onStop,
            boolean bufferize) {

        super(stateMachine);
        this.successSequence = successSequence;
        this.stopSequence = stopSequence;
        successSequenceCursor = 0;
        stopSequenceCursor = 0;
        this.onSuccess = onSuccess;
        this.onStop = onStop;
        this.bufferize = bufferize;

    }


    @Override
    public int push(byte b) {

        if(bufferize)
            bufferize(b);

        if(successSequence[successSequenceCursor] == b) {
            successSequenceCursor++;
            if(successSequenceCursor == successSequence.length)
                return onSuccess != null ? onSuccess.run(getBuffer(), stateMachine, this) : stateMachine.CUT;
        } else
            successSequenceCursor = 0;

        if(stopSequence != null && stopSequence[stopSequenceCursor] == b) {
            stopSequenceCursor++;
            if(stopSequence != null && stopSequenceCursor == stopSequence.length)
                return onStop != null ? onStop.run(getBuffer(), stateMachine, this) : stateMachine.STOP;
        } else
            stopSequenceCursor = 0;

        return stateMachine.getCurrentStateIndex();

    }


    @Override
    public void reset() {

        super.reset();

        successSequenceCursor = 0;
        stopSequenceCursor = 0;

    }
}
