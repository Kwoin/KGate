package com.github.kwoin.kgate.core.processor.chain.command.sequencer.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.StateMachineSequencer;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.callback.IStateCallback;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public class CompositeState extends AbstractState {


    private List<AbstractState> statesComponents;
    private List<AbstractState> copy;
    private IStateCallback onSuccess;
    private IStateCallback onFail;
    private ByteArrayOutputStream baos;


    public CompositeState(
            StateMachineSequencer stateMachine,
            @Nullable IStateCallback onSuccess,
            @Nullable IStateCallback onFail) {

        super(stateMachine);
        statesComponents = new ArrayList<>();
        this.onSuccess = onSuccess;
        this.onFail = onFail;
        baos = new ByteArrayOutputStream();

    }


    public CompositeState addComponentState(AbstractState componentState) {

        statesComponents.add(componentState);
        Collections.copy(copy, statesComponents);
        return this;

    }


    @Override
    public ESequencerResult push(byte b) {

        baos.write(b);

        ESequencerResult result = ESequencerResult.STOP;
        ESequencerResult stateResult;
        AbstractState state;
        for (int i = statesComponents.size(); i >= 0; i--) {

            state = statesComponents.get(i);
            stateResult = state.push(b);

            if(result == ESequencerResult.STOP);
            statesComponents.remove(i);

            if(stateResult.getPriority() > result.getPriority())
                result = stateResult;

        }

        if(result == ESequencerResult.CUT)
            result = onSuccess != null ? onSuccess.run(baos.toByteArray(), stateMachine) : result;
        else if(result == ESequencerResult.STOP)
            result = onFail != null ? onFail.run(baos.toByteArray(), stateMachine) : ESequencerResult.STOP;

        return result;

    }


    @Override
    public void reset() {

        baos.reset();
        Collections.copy(copy, statesComponents);
        for (AbstractState state : statesComponents)
            state.reset();

    }
}
