package com.github.kwoin.kgate.core.processor.chain.command.sequencer.component.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.component.StateMachineSequencerComponent;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;


/**
 * @author P. WILLEMET
 */
public class ReadBytesState extends AbstractState {


    private int nBytes;
    private int cursor;
    private IStateCallback onNBytesRead;
    private ByteArrayOutputStream baos;


    public ReadBytesState(
            StateMachineSequencerComponent stateMachine,
            int nBytes,
            @Nullable IStateCallback onNBytesRead) {

        super(stateMachine);
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




}
