package com.github.kwoin.kgate.http.sequencing.state;

import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.http.sequencing.HttpRequestSequencer;
import com.github.kwoin.kgate.http.sequencing.callback.RequestReadRequestUriCallback;


/**
 * @author P. WILLEMET
 */
public class RequestReadRequestUriState extends ReadUntilSequenceState<HttpRequestSequencer> {


    public RequestReadRequestUriState(HttpRequestSequencer stateMachine) {

        super(stateMachine,
                " ".getBytes(),
                "\r".getBytes(),
                new RequestReadRequestUriCallback(),
                null,
                true);
    }
}
