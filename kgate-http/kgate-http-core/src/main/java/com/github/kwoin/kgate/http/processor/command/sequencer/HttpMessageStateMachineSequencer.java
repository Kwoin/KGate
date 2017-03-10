package com.github.kwoin.kgate.http.processor.command.sequencer;

import com.github.kwoin.kgate.core.context.EDirection;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.processor.command.sequencer.StateMachineSequencer;
import com.github.kwoin.kgate.core.processor.command.sequencer.state.AbstractState;
import com.github.kwoin.kgate.core.processor.command.sequencer.state.ReadNBytesState;
import com.github.kwoin.kgate.core.processor.command.sequencer.state.StopState;
import com.github.kwoin.kgate.http.processor.command.HttpReadRequestMethodCommand;
import com.github.kwoin.kgate.http.processor.command.sequencer.state.FinishHeaderState;
import com.github.kwoin.kgate.http.processor.command.sequencer.state.FinishStartLineState;
import com.github.kwoin.kgate.http.processor.command.sequencer.state.NoBodyState;
import com.github.kwoin.kgate.http.processor.command.sequencer.state.ReadChunkState;
import com.github.kwoin.kgate.http.processor.command.sequencer.state.ReadContentLengthState;
import com.github.kwoin.kgate.http.processor.command.sequencer.state.ReadHeaderState;
import com.github.kwoin.kgate.http.processor.command.sequencer.state.ReadHttpVersionState;
import com.github.kwoin.kgate.http.processor.command.sequencer.state.ReadResponseCodeState;
import com.github.kwoin.kgate.http.processor.command.sequencer.state.ReadTransferEncodingState;


/**
 * @author P. WILLEMET
 */
public class HttpMessageStateMachineSequencer extends StateMachineSequencer {


    public static final int READ_HTTP_VERSION_STATE = 0;
    public static final int READ_RESPONSE_CODE_STATE = 1;
    public static final int NO_BODY_STATE = 2;
    public static final int FINISH_START_LINE_STATE = 3;
    public static final int READ_HEADER_STATE = 4;
    public static final int FINISH_HEADER_STATE = 5;
    public static final int READ_BODY_STATE = 6;
    public static final int READ_CONTENT_LENGTH_STATE = 7;
    public static final int READ_TRANSFER_ENCODING_STATE = 8;

    private boolean chunked;
    private int contentLength;


    public HttpMessageStateMachineSequencer(IContext context) {

        super();

        if(context.getVariable(IContext.ECoreScope.SESSION, EDirection.DIRECTION_FIELD) == EDirection.RESPONSE
                && context.getVariable(IContext.ECoreScope.SESSION, HttpReadRequestMethodCommand.REQUEST_METHOD_FIELD).equals("HEAD"))
        currentStateIndex = 2;

    }


    @Override
    public AbstractState[] initializeStates() {

        return new AbstractState[] {new ReadHttpVersionState(this),
                new ReadResponseCodeState(this),
                new NoBodyState(this),
                new FinishStartLineState(this),
                new ReadHeaderState(this),
                new FinishHeaderState(this),
                new StopState(this),
                new ReadContentLengthState(this),
                new ReadTransferEncodingState(this)
        };

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



    public int computeReadBodyState() {

        if(!chunked && contentLength == 0)
            return NO_BODY_STATE;
        else {
            states[6] = !chunked && contentLength > 0
                    ? new ReadNBytesState(this, contentLength, null, false)
                    : new ReadChunkState(this);
            return FINISH_HEADER_STATE;
        }

    }



}
