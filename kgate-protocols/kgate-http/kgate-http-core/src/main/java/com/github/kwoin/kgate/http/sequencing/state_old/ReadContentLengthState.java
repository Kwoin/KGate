package com.github.kwoin.kgate.http.sequencing.state_old;


import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.http.sequencing.state_old.callback.RegisterContentLengthCallback;


/**
 * @author P. WILLEMET
 */
public class ReadContentLengthState extends ReadUntilSequenceState {


    public ReadContentLengthState(IStateMachine stateMachine) {

        super(stateMachine,
                "\r\n".getBytes(),
                null,
                new RegisterContentLengthCallback(),
                null,
                true);

    }

}
