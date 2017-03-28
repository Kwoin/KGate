package com.github.kwoin.kgate.core.sequencing.state;

import com.github.kwoin.kgate.core.sequencing.IStateMachine;

import java.io.ByteArrayOutputStream;


/**
 * @author P. WILLEMET
 */
public abstract class AbstractState<T extends IStateMachine> {


    protected T stateMachine;
    private ByteArrayOutputStream baos;


    public AbstractState(T stateMachine) {

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