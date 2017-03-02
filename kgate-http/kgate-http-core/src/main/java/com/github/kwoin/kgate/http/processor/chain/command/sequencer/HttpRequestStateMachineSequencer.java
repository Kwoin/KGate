package com.github.kwoin.kgate.http.processor.chain.command.sequencer;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.StateMachineSequencer;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.ReadNBytesState;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.ReadUntilSequenceState;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.callback.NextStateCallback;
import com.github.kwoin.kgate.http.processor.chain.command.sequencer.state.ReadMethodState;


/**
 * @author P. WILLEMET
 */
public class HttpRequestStateMachineSequencer extends StateMachineSequencer {


    private boolean chunked;
    private int contentLength;


    public HttpRequestStateMachineSequencer() {

        super();

        initialAdd(new ReadMethodState(this));
        initialAdd(new ReadNBytesState(this, 1, new NextStateCallback()));
        initialAdd(new ReadUntilSequenceState(this, " ".getBytes(), null, new NextStateCallback(), null));
        initialAdd(new ReadUntilSequenceState(this, "\r\n".getBytes(), null, new NextStateCallback(), null));
        initialAdd(new ReadUntilSequenceState(this, "\r\n".getBytes(), null, ))


    }


    public boolean isChunked() {

        return chunked;

    }


    public void setChunked(boolean chunked) {

        this.chunked = chunked;

    }


    public int getContentLength() {

        return contentLength;

    }


    public void setContentLength(int contentLength) {

        this.contentLength = contentLength;

    }
}
