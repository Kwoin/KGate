package com.github.kwoin.kgate.http.sequencing.state;

import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.http.sequencing.HttpRequestSequencer;
import com.github.kwoin.kgate.http.sequencing.callback.RequestReadMethodCallback;


/**
 * @author P. WILLEMET
 */
public class RequestReadMethodState extends ReadUntilSequenceState<HttpRequestSequencer> {


    public RequestReadMethodState(HttpRequestSequencer stateMachine) {

        super(stateMachine,
                " ".getBytes(),
                null,
                new RequestReadMethodCallback(),
                null,
                true);

    }
}
