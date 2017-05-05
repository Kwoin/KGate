package com.github.kwoin.kgate.http.sequencing.state;

import com.github.kwoin.kgate.core.sequencing.state.ReadNBytesState;
import com.github.kwoin.kgate.http.sequencing.HttpMessageSequencer;
import com.github.kwoin.kgate.http.sequencing.callback.ReadBodyCallback;


/**
 * @author P. WILLEMET
 */
public class ReadBodyState extends ReadNBytesState<HttpMessageSequencer> {


    public ReadBodyState(HttpMessageSequencer stateMachine) {

        super(stateMachine,
                1,
                new ReadBodyCallback(),
                true);

    }
}
