package com.github.kwoin.kgate.sequencing.state;

import com.github.kwoin.kgate.sequencing.IStateMachine;
import com.github.kwoin.kgate.sequencing.state.callback.IStateCallback;

import javax.annotation.Nullable;


/**
 * @author P. WILLEMET
 */
public class ReadNBytesState extends AbstractState {


    private int nBytes;
    private int cursor;
    private IStateCallback onNBytesRead;
    private boolean bufferize;


    public ReadNBytesState(
            IStateMachine stateMachine,
            int nBytes,
            @Nullable IStateCallback onNBytesRead,
            boolean bufferize) {

        super(stateMachine);

        if(nBytes < 1)
            throw new IllegalArgumentException("nBytes must be >= 1");

        this.nBytes = nBytes;
        cursor = 0;
        this.onNBytesRead = onNBytesRead;
        this.bufferize = bufferize;

    }


    public int getNBytes() {

        return nBytes;

    }


    public void setNBytes(int nBytes) {

        this.nBytes = nBytes;

    }


    @Override
    public int push(byte b) {

        if (bufferize)
            bufferize(b);

        if(++cursor == nBytes)
            return onNBytesRead != null ? onNBytesRead.run(getBuffer(), stateMachine, this) : stateMachine.CUT;

        return stateMachine.getCurrentStateIndex();

    }


    @Override
    public void reset() {

        super.reset();

        cursor = 0;

    }

}
