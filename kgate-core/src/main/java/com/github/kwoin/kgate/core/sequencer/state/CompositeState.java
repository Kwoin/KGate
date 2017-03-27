package com.github.kwoin.kgate.core.sequencer.state;

import com.github.kwoin.kgate.core.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.sequencer.state.callback.IStateCallback;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public class CompositeState extends AbstractState {


    private List<AbstractState> statesComponents;
    private List<AbstractState> copy;
    private IStateCallback onSuccess;
    private IStateCallback onFail;
    private boolean bufferize;
    private boolean resetOnSwitchingState;


    public CompositeState(
            IStateMachine stateMachine,
            @Nullable IStateCallback onSuccess,
            @Nullable IStateCallback onFail,
            boolean bufferize,
            boolean resetOnSwitchingState) {

        super(stateMachine);
        statesComponents = new ArrayList<>();
        copy = new ArrayList<>();
        this.onSuccess = onSuccess;
        this.onFail = onFail;
        this.bufferize = bufferize;
        this.resetOnSwitchingState = resetOnSwitchingState;

    }


    public CompositeState addComponentState(AbstractState componentState) {

        statesComponents.add(componentState);
        copy.add(componentState);
        return this;

    }


    @Override
    public int push(byte b) {

        if(bufferize)
            bufferize(b);

        int result;
        for (int i = copy.size() - 1; i >= 0; i--) {

            result = copy.get(i).push(b);

            if(result == stateMachine.STOP)
                copy.remove(i);
            else if(result == stateMachine.CUT)
                return onSuccess != null ? onSuccess.run(getBuffer(), stateMachine, this) : result;
            else if(result != stateMachine.getCurrentStateIndex()) {
                if(resetOnSwitchingState)
                    reset();
                return result;
            }

        }

        if(copy.size() == 0)
            return onFail != null ? onFail.run(getBuffer(), stateMachine, this) : stateMachine.STOP;

        return stateMachine.getCurrentStateIndex();

    }


    @Override
    public void reset() {

        super.reset();


        copy.clear();
        copy.addAll(statesComponents);
        for (AbstractState state : statesComponents)
            state.reset();

    }
}
