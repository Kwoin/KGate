package com.github.kwoin.kgate.http.sequencing.state;

import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.http.sequencing.HttpRequestSequencer;
import com.github.kwoin.kgate.http.sequencing.callback.ReadHttpVersionCallback;


/**
 * @author P. WILLEMET
 */
public class RequestReadHttpVersionState extends ReadUntilSequenceState<HttpRequestSequencer> {


    public RequestReadHttpVersionState(HttpRequestSequencer stateMachine) {

        super(stateMachine,
                "\r\n".getBytes(),
                null,
                new ReadHttpVersionCallback(HttpRequestSequencer.READ_HEADER, 2),
                null,
                true);
    }
}
