package com.github.kwoin.kgate.http.sequencing.state;

import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.http.sequencing.HttpMessageSequencer;
import com.github.kwoin.kgate.http.sequencing.HttpResponseSequencer;
import com.github.kwoin.kgate.http.sequencing.callback.ReadHttpVersionCallback;


/**
 * @author P. WILLEMET
 */
public class ResponseReadHttpVersionState extends ReadUntilSequenceState<HttpMessageSequencer> {


    public ResponseReadHttpVersionState(HttpMessageSequencer stateMachine) {

        super(stateMachine,
                " ".getBytes(),
                "\r".getBytes(),
                new ReadHttpVersionCallback(HttpResponseSequencer.READ_STATUS_CODE, 1),
                null,
                true);

    }
}
