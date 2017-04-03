package com.github.kwoin.kgate.http.sequencing.state;

import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.http.sequencing.HttpResponseSequencer;
import com.github.kwoin.kgate.http.sequencing.callback.ResponseReadStatusCodeCallback;


/**
 * @author P. WILLEMET
 */
public class ResponseReadStatusCodeState extends ReadUntilSequenceState<HttpResponseSequencer> {


    public ResponseReadStatusCodeState(HttpResponseSequencer stateMachine) {

        super(stateMachine,
                " ".getBytes(),
                "\r".getBytes(),
                new ResponseReadStatusCodeCallback(),
                null,
                true);

    }
}
