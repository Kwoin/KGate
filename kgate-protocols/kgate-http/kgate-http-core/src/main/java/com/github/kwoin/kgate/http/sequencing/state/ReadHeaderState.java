package com.github.kwoin.kgate.http.sequencing.state;


import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.http.sequencing.HttpMessageSequencer;
import com.github.kwoin.kgate.http.sequencing.callback.ReadHeaderCallback;


/**
 * @author P. WILLEMET
 */
public class ReadHeaderState extends ReadUntilSequenceState<HttpMessageSequencer> {


    public ReadHeaderState(HttpMessageSequencer stateMachine) {

        super(stateMachine,
                "\r\n".getBytes(),
                null,
                new ReadHeaderCallback(),
                null,
                true);

    }
}
