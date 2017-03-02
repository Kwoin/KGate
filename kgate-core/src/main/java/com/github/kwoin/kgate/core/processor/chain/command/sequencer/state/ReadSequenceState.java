package com.github.kwoin.kgate.core.processor.chain.command.sequencer.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.StateMachineSequencer;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.callback.IStateCallback;

import javax.annotation.Nullable;


/**
 * @author P. WILLEMET
 */
public class ReadSequenceState extends AbstractState {


    private byte[] sequence;
    private int sequenceCursor;
    private IStateCallback onSuccess;
    private IStateCallback onFail;


    public ReadSequenceState(
            StateMachineSequencer stateMachine,
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
    public ESequencerResult push(byte b) {

        if(sequence[sequenceCursor] == b)
            sequenceCursor++;
        else if(onFail != null) {
            byte[] dataRead = new byte[sequenceCursor + 1];
            System.arraycopy(sequence, 0, dataRead, 0, sequenceCursor);
            dataRead[dataRead.length - 1] = b;
            onFail.run(dataRead, stateMachine);
        } else
            return ESequencerResult.STOP;

        if(sequenceCursor == sequence.length)
            return onSuccess != null ? onSuccess.run(sequence, stateMachine) : ESequencerResult.CUT;

        return ESequencerResult.CONTINUE;

    }


    @Override
    public void reset() {

        sequenceCursor = 0;

    }

}
