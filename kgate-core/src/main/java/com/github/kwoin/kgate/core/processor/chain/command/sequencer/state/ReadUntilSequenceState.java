package com.github.kwoin.kgate.core.processor.chain.command.sequencer.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.StateMachineSequencer;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.callback.IStateCallback;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;


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
    private ByteArrayOutputStream baos;


    public ReadUntilSequenceState(
            StateMachineSequencer stateMachine,
            byte[] successSequence,
            @Nullable byte[] stopSequence,
            @Nullable IStateCallback onSuccess,
            @Nullable IStateCallback onStop) {

        super(stateMachine);
        this.successSequence = successSequence;
        this.stopSequence = stopSequence;
        successSequenceCursor = 0;
        stopSequenceCursor = 0;
        this.onSuccess = onSuccess;
        this.onStop = onStop;
        baos = new ByteArrayOutputStream();

    }


    @Override
    public int push(byte b) {

        baos.write(b);

        if(successSequence[successSequenceCursor] == b)
            stopSequenceCursor++;

        if(stopSequence != null && stopSequence[stopSequenceCursor] == b)
            stopSequenceCursor++;

        if(successSequenceCursor == successSequence.length)
            return onSuccess != null ? onSuccess.run(baos.toByteArray(), stateMachine) : stateMachine.CUT;

        if(stopSequence != null && stopSequenceCursor == stopSequence.length)
            return onStop != null ? onStop.run(baos.toByteArray(), stateMachine) : stateMachine.STOP;

        return stateMachine.getCurrentStateIndex();

    }


    @Override
    public void reset() {

        baos.reset();
        successSequenceCursor = 0;
        stopSequenceCursor = 0;

    }
}
