package com.github.kwoin.kgate.core.processor.chain.command.sequencer.state;

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
    public int push(byte b) {

        baos.write(b);

        int result;
        for (int i = copy.size(); i >= 0; i--) {

            result = copy.get(i).push(b);

            if(result == stateMachine.STOP)
                copy.remove(i);
            else if(result == stateMachine.CUT)
                return onSuccess != null ? onSuccess.run(baos.toByteArray(), stateMachine) : result;
            else if(result != stateMachine.getCurrentStateIndex())
                return result;

        }

        if(copy.size() == 0)
            return onFail != null ? onFail.run(baos.toByteArray(), stateMachine) : stateMachine.STOP;

        return stateMachine.getCurrentStateIndex();

    }


    @Override
    public void reset() {

        baos.reset();
        Collections.copy(copy, statesComponents);
        for (AbstractState state : statesComponents)
            state.reset();

    }
}
