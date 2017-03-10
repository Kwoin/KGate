package com.github.kwoin.kgate.core.processor.command.sequencer.state;

import com.github.kwoin.kgate.core.processor.command.sequencer.IStateMachine;

import java.io.ByteArrayOutputStream;


/**
 * @author P. WILLEMET
 */
public abstract class AbstractState {


    protected IStateMachine stateMachine;
    private ByteArrayOutputStream baos;


    public AbstractState(IStateMachine stateMachine) {

        this.stateMachine = stateMachine;
        baos = new ByteArrayOutputStream();

    }


    public abstract int push(byte b);


    protected void bufferize(byte b) {

        baos.write(b);

    }

    protected byte[] getBuffer() {

        return baos.toByteArray();

    }


    public void reset() {

        baos.reset();

    }


}