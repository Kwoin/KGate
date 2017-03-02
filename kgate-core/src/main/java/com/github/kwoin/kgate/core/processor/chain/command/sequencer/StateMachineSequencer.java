package com.github.kwoin.kgate.core.processor.chain.command.sequencer;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.AbstractState;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;


/**
 * @author P. WILLEMET
 */
public class StateMachineSequencer implements IStateMachineSequencer {


    private List<AbstractState> states;
    private boolean started;
    private AbstractState currentState;
    private ListIterator<AbstractState> it;
    private Queue<AbstractState> dynamicallyAddedStates;


    public StateMachineSequencer() {

        states = new ArrayList<>();
        started = false;

    }


    @Override
    public ESequencerResult push(byte b) {

        ESequencerResult result = currentState.push(b);
        if(result != ESequencerResult.CONTINUE)
            reset();

        return result;

    }


    @Override
    public IStateMachineSequencer initialAdd(AbstractState state) {

        if(started)
            throw new IllegalStateException("Cannot add initial states while StateMachine is started");

        states.add(state);
        return this;

    }


    @Override
    public IStateMachineSequencer start() {

        it = states.listIterator();
        dynamicallyAddedStates = new ArrayDeque<>();
        started = true;
        return this;

    }


    @Override
    public void reset() {

        started = false;
        dynamicallyAddedStates.clear();
        currentState = null;
        it = null;

    }



    @Override
    public boolean hasNext() {

        return dynamicallyAddedStates.size() > 0 || it.hasNext();

    }


    @Override
    public AbstractState next() {

        currentState = dynamicallyAddedStates.size() > 0 ? dynamicallyAddedStates.poll() : it.next();
        return currentState;

    }


    @Override
    public void remove() {

        throw new UnsupportedOperationException();

    }


    @Override
    public void add(AbstractState state) {

        dynamicallyAddedStates.add(state);

    }


}
