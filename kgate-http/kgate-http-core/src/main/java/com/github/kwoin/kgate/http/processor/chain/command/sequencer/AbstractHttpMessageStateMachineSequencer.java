package com.github.kwoin.kgate.http.processor.chain.command.sequencer;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.StateMachineSequencer;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.AbstractState;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.state.ReadNBytesState;

import javax.annotation.Nullable;


/**
 * @author P. WILLEMET
 */
public class AbstractHttpMessageStateMachineSequencer extends StateMachineSequencer {



    private boolean chunked;
    private int contentLength;


    public AbstractHttpMessageStateMachineSequencer() {

        super();

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


    @Nullable
    public AbstractState computeReadBodyState() {

        if(!chunked && contentLength == 0)
            return null;
        else if(!chunked && contentLength > 0)
            return new ReadNBytesState(this, contentLength, null);
        else
            return new ReadChunckedBody();

    }



}
