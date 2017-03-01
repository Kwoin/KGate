package com.github.kwoin.kgate.core.processor.chain.command.sequencer.component.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.component.StateMachineSequencerComponent;

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
            StateMachineSequencerComponent stateMachine,
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
    public ESequencerResult push(byte b) {

        baos.write(b);

        if(successSequence[successSequenceCursor] == b)
            stopSequenceCursor++;

        if(stopSequence != null && stopSequence[stopSequenceCursor] == b)
            stopSequenceCursor++;

        if(successSequenceCursor == successSequence.length)
            return onSuccess != null ? onSuccess.run(baos.toByteArray(), stateMachine) : ESequencerResult.CUT;

        if(stopSequence != null && stopSequenceCursor == stopSequence.length)
            return onStop != null ? onStop.run(baos.toByteArray(), stateMachine) : ESequencerResult.STOP;

        return ESequencerResult.CONTINUE;

    }
}
