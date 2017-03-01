package com.github.kwoi.kgate.http.processor.chain.command.sequencer.component.state;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.component.StateMachineSequencerComponent;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.component.state.AbstractState;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.component.state.IStateCallback;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.component.state.ReadUntilSequenceState;


/**
 * @author P. WILLEMET
 */
public class ReadHeadersState extends ReadUntilSequenceState {


    public ReadHeadersState(StateMachineSequencerComponent stateMachine) {

        super(
                stateMachine,
                "\r\n\r\n".getBytes(),
                null,
        );

    }


    @Override
    public ESequencerResult push(byte b) {

        return null;

    }


    private class HeadersCallback implements IStateCallback {


        @Override
        public ESequencerResult run(byte[] dataRead, StateMachineSequencerComponent stateMachine) {

            String headers = new String(dataRead, 0, dataRead.length - 4);
            String[] lines = headers.split("\\r\\n");
            String[] header;
            for (String line : lines) {
                header = line.split(":", 1);
                header[]
            }

        }

    }

}
