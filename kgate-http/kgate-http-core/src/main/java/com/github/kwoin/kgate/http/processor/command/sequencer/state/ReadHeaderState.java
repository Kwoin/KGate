package com.github.kwoin.kgate.http.processor.command.sequencer.state;

import com.github.kwoin.kgate.core.sequencer.IStateMachine;
import com.github.kwoin.kgate.core.sequencer.state.CompositeState;
import com.github.kwoin.kgate.core.sequencer.state.ReadSequenceState;
import com.github.kwoin.kgate.core.sequencer.state.callback.SwitchStateCallback;
import com.github.kwoin.kgate.http.processor.command.sequencer.HttpMessageStateMachineSequencer;


/**
 * @author P. WILLEMET
 */
public class ReadHeaderState extends CompositeState {


    public ReadHeaderState(IStateMachine stateMachine) {

        super(stateMachine,
                null,
                new SwitchStateCallback(HttpMessageStateMachineSequencer.FINISH_HEADER_STATE, true),
                false,
                true);

        addComponentState(new ReadSequenceState(stateMachine,
                "Content-Length: ".getBytes(),
                new SwitchStateCallback(HttpMessageStateMachineSequencer.READ_CONTENT_LENGTH_STATE, false),
                null));
        addComponentState(new ReadSequenceState(stateMachine,
                "Transfer-Encoding:".getBytes(),
                new SwitchStateCallback(HttpMessageStateMachineSequencer.READ_TRANSFER_ENCODING_STATE, false),
                null));
        addComponentState(new ReadSequenceState(stateMachine,
                "\r\n".getBytes(),
                (dataRead, stateMachine1, callingState) ->
                        ((HttpMessageStateMachineSequencer) stateMachine1).computeReadBodyState() == HttpMessageStateMachineSequencer.NO_BODY_STATE
                        ? HttpMessageStateMachineSequencer.CUT
                        : HttpMessageStateMachineSequencer.READ_BODY_STATE,
                null));

    }
}
