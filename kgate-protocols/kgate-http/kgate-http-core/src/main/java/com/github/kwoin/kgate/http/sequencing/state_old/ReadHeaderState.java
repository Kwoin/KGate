package com.github.kwoin.kgate.http.sequencing.state_old;


import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.CompositeState;
import com.github.kwoin.kgate.core.sequencing.state.ReadSequenceState;
import com.github.kwoin.kgate.core.sequencing.state.callback.SwitchStateCallback;
import com.github.kwoin.kgate.http.sequencing.HttpMessageStateMachineSequencer;


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
