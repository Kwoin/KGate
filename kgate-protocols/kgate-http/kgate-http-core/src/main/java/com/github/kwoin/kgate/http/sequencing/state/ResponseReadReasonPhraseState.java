package com.github.kwoin.kgate.http.sequencing.state;

import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.http.sequencing.HttpResponseSequencer;
import com.github.kwoin.kgate.http.sequencing.callback.ResponseReadReasonPhraseCallback;


/**
 * @author P. WILLEMET
 */
public class ResponseReadReasonPhraseState extends ReadUntilSequenceState<HttpResponseSequencer> {


    public ResponseReadReasonPhraseState(HttpResponseSequencer stateMachine) {

        super(stateMachine,
                "\r\n".getBytes(),
                null,
                new ResponseReadReasonPhraseCallback(),
                null,
                true);
    }
}
