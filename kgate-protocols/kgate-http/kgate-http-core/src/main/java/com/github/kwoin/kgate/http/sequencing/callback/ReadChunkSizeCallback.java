package com.github.kwoin.kgate.http.sequencing.callback;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.sequencing.IStateMachine;
import com.github.kwoin.kgate.core.sequencing.state.AbstractState;
import com.github.kwoin.kgate.core.sequencing.state.ReadNBytesState;
import com.github.kwoin.kgate.core.sequencing.state.callback.IStateCallback;
import com.github.kwoin.kgate.http.model.HttpResponse;
import com.github.kwoin.kgate.http.sequencing.HttpMessageSequencer;
import com.github.kwoin.kgate.http.sequencing.state.ReadBodyChunkedState;


/**
 * @author P. WILLEMET
 */
public class ReadChunkSizeCallback implements IStateCallback<ReadBodyChunkedState> {


    @Override
    public int run(byte[] dataRead, ReadBodyChunkedState stateMachine, AbstractState callingState) {

        callingState.reset();

        String chunkSizeHex = null;
        for (int i = 0; i < dataRead.length; i++) {
            int read = dataRead[i];
            if(!(read >= 'a' && read <= 'z'
                    || read >= 'A' && read <= 'Z'
                    || read >= '0' && read <= '9')) {
                chunkSizeHex = new String(dataRead, 0, i);
                break;
            }
        }
        try {
            int chunkSize = Integer.parseInt(chunkSizeHex, 16);
            if(chunkSize > 0) {
                ((ReadNBytesState) stateMachine.getStates()[ReadBodyChunkedState.READ_CHUNK_DATA]).setNBytes(chunkSize);
                return ReadBodyChunkedState.READ_CHUNK_DATA;
            } else {
                return ReadBodyChunkedState.READ_FINAL_CRLF;
            }
        } catch (NumberFormatException e) {
            HttpResponse errorResponse = new HttpResponse();
            errorResponse.setHttpVersion("HTTP/1.1");
            errorResponse.setStatusCode("418");
            errorResponse.setReasonPhrase("I'm a teapot");
            stateMachine.getContext().setVariable(IContext.ECoreScope.SESSION, HttpMessageSequencer.HTTP_SEQUENCER_ERROR_FIELD, errorResponse);
            return IStateMachine.STOP;
        }

    }
}
