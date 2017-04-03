package com.github.kwoin.kgate.http.sequencing.state;

import com.github.kwoin.kgate.core.sequencing.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.http.sequencing.callback.ReadChunkSizeCallback;


/**
 * @author P. WILLEMET
 */
public class ReadChunkSizeState extends ReadUntilSequenceState<ReadBodyChunkedState> {


    public ReadChunkSizeState(ReadBodyChunkedState stateMachine) {

        super(stateMachine,
                "\r\n".getBytes(),
                null,
                new ReadChunkSizeCallback(),
                null,
                true);

    }
}
