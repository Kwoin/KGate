package com.github.kwoin.kgate.core.processor.chain.command.sequencer.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.StateMachineSequencer;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.callback.IStateCallback;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public class RecursiveState extends AbstractState {


    private List<AbstractState> states;
    private IStateCallback onSuccess;
    private IStateCallback onFail;
    private ByteArrayOutputStream baos;


    public RecursiveState(StateMachineSequencer stateMachine,
                          @Nullable IStateCallback onSuccess,
                          @Nullable IStateCallback onFail) {

        super(stateMachine);
        states = new ArrayList<>();
        this.onSuccess = onSuccess;
        this.onFail = onFail;
        baos = new ByteArrayOutputStream();

    }


    public RecursiveState addState(AbstractState state) {

        states.add(state);
        return this;

    }

    @Override
    public ESequencerResult push(byte b) {

        // add a reset method to State contract ? test

    }
}
