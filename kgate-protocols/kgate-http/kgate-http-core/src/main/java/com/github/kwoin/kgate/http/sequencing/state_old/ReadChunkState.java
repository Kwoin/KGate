package com.github.kwoin.kgate.http.sequencing.state_old;


import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.core.sequencing.state.ReadNBytesState;
import com.github.kwoin.kgate.core.sequencing.state.ReadSequenceState;
import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.core.sequencing.state.callback.IStateCallback;
import com.github.kwoin.kgate.http.sequencing.state_old.callback.IsEndCallback;


/**
 * @author P. WILLEMET
 */
public class ReadChunkState extends AbstractState implements IStateMachine {


    private int chunkLength;
    private int currentState;
    private final AbstractState[] states = {
            new ReadUntilSequenceState(
                    this,
                    "\r\n".getBytes(),
                    null,
                    new IStateCallback() {
                        @Override
                        public int run(byte[] dataRead, IStateMachine stateMachine, AbstractState callingState) {
                            int chunkLength = Integer.parseInt(new String(dataRead, 0 , dataRead.length - 2));
                            if(chunkLength == 0)
                                return 2;
                            ((ReadChunkState) stateMachine).getNBytesReader().setNBytes(chunkLength + 2);
                            callingState.reset();
                            return 1;
                        }
                    },
                    null,
                    true),
            new ReadNBytesState(
                    this,
                    1,
                    new IsEndCallback(),
                    true),
            new ReadSequenceState(
                    this,
                    "\r\n".getBytes(),
                    null,
                    null
            )};


    public ReadChunkState(IStateMachine stateMachine) {

        super(stateMachine);

    }


    public ReadNBytesState getNBytesReader() {

        return (ReadNBytesState)states[1];

    }


    @Override
    public int getCurrentStateIndex() {

        return currentState;

    }


    @Override
    public void setCurrentStateIndex(int currentStateIndex) {

        this.currentState = currentStateIndex;

    }


    @Override
    public AbstractState[] getStates() {

        return states;

    }


    @Override
    public int push(byte b) {

        int result = states[currentState].push(b);
        if(result >= 0) {
            currentState = result;
            return stateMachine.getCurrentStateIndex();
        } else
            return result;

    }
}
