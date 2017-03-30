package com.github.kwoin.kgate.http.sequencing.state;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.core.sequencing.state.ReadNBytesState;
import com.github.kwoin.kgate.core.sequencing.state.callback.SwitchStateCallback;
import com.github.kwoin.kgate.http.sequencing.HttpMessageSequencer;


/**
 * @author P. WILLEMET
 */
public class ReadBodyChunkedState extends AbstractState<HttpMessageSequencer> implements IStateMachine {


    public static final int READ_CHUNK_SIZE = 0;
    public static final int READ_CHUNK_DATA = 1;
    public static final int READ_TRAILING_CRLF = 2;
    public static final int READ_FINAL_CRLF = 3;


    private int currentStateIndex;
    private AbstractState<ReadBodyChunkedState>[] states;


    public ReadBodyChunkedState(HttpMessageSequencer stateMachine) {

        super(stateMachine);
        currentStateIndex = 0;
        states = new AbstractState[] {
                new ReadChunkSizeState(this),
                new ReadNBytesState(this, 1, new SwitchStateCallback(READ_TRAILING_CRLF, true), false),
                new ReadNBytesState(this, 2, new SwitchStateCallback(READ_CHUNK_SIZE, true), false),
                new ReadNBytesState(this, 2, new SwitchStateCallback(IStateMachine.CUT, false), false),
        };

    }


    @Override
    public int getCurrentStateIndex() {

        return currentStateIndex;

    }


    @Override
    public void setCurrentStateIndex(int currentStateIndex) {

        this.currentStateIndex = currentStateIndex;

    }


    @Override
    public AbstractState[] getStates() {

        return states;

    }


    @Override
    public int push(byte b) {

        if(currentStateIndex == READ_CHUNK_DATA)
            bufferize(b);

        currentStateIndex = states[currentStateIndex].push(b);

        if(currentStateIndex < 0) {
            stateMachine.getHttpMessage().setBody(new String(getBuffer()));
            return currentStateIndex;
        } else
            return stateMachine.getCurrentStateIndex();

    }


    @Override
    public void reset() {

        super.reset();
        currentStateIndex = 0;
        for (AbstractState<ReadBodyChunkedState> state : states)
            state.reset();

    }


    public IContext getContext() {

        return stateMachine.getContext();

    }

}
