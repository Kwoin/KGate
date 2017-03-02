package com.github.kwoin.kgate.core.processor.chain.command.sequencer;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.AbstractState;

import java.util.Iterator;


/**
 * @author P. WILLEMET
 */
public interface IStateMachineSequencer extends ISequencer, Iterator<AbstractState> {


    IStateMachineSequencer initialAdd(AbstractState state);

    IStateMachineSequencer start();

    void add(AbstractState state);

    void reset();


}
