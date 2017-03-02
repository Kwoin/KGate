package com.github.kwoin.kgate.core.processor.chain.command.sequencer.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.StateMachineSequencer;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.callback.IStateCallback;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;


/**
 * @author P. WILLEMET
 */
public class ReadNBytesState extends AbstractState {


    private int nBytes;
    private int cursor;
    private IStateCallback onNBytesRead;
    private ByteArrayOutputStream baos;


    public ReadNBytesState(
            StateMachineSequencer stateMachine,
            int nBytes,
            @Nullable IStateCallback onNBytesRead) {

        super(stateMachine);

        if(nBytes < 1)
            throw new IllegalArgumentException("nBytes must be >= 1");

        this.nBytes = nBytes;
        cursor = 0;
        this.onNBytesRead = onNBytesRead;
        baos = new ByteArrayOutputStream();

    }


    @Override
    public ESequencerResult push(byte b) {

        baos.write(b);

        if(++cursor == nBytes)
            return onNBytesRead != null ? onNBytesRead.run(baos.toByteArray(), stateMachine) : ESequencerResult.CUT;

        return ESequencerResult.CONTINUE;

    }


    @Override
    public void reset() {

        baos.reset();
        cursor = 0;

    }

}
