package com.github.kwoin.kgate.http.processor.chain.command.sequencer.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.StateMachineSequencer;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.CompositeState;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.callback.NextStateCallback;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.ReadSequenceState;


/**
 * @author P. WILLEMET
 */
public class ReadMethodState extends CompositeState {


    public ReadMethodState(StateMachineSequencer stateMachine) {

        super(stateMachine, new NextStateCallback(), null);

        addComponentState(new ReadSequenceState(stateMachine, "OPTIONS".getBytes(), null, null));
        addComponentState(new ReadSequenceState(stateMachine, "GET".getBytes(), null, null));
        addComponentState(new ReadSequenceState(stateMachine, "HEAD".getBytes(), null, null));
        addComponentState(new ReadSequenceState(stateMachine, "POST".getBytes(), null, null));
        addComponentState(new ReadSequenceState(stateMachine, "PUT".getBytes(), null, null));
        addComponentState(new ReadSequenceState(stateMachine, "DELETE".getBytes(), null, null));
        addComponentState(new ReadSequenceState(stateMachine, "TRACE".getBytes(), null, null));
        addComponentState(new ReadSequenceState(stateMachine, "CONNECT".getBytes(), null, null));

    }


}
